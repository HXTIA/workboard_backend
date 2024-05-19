package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.mapper.AuthorizationMapper;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.dto.CodesInfoDto;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.*;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.*;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AuthorizationVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;
import run.hxtia.workbd.service.usermanagement.CodesService;
import run.hxtia.workbd.service.usermanagement.StudentAuthorizationService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, Authorization> implements AuthorizationService {
    // Redis
    private final Redises redises;

    // 课程
    private final CourseService courseService;

    // 年级
    private final GradeService gradeService;
    // 班级
    private final ClassService classService;

    // 授权码
    private final CodesService codesService;

    // 学生
    private final StudentService studentService;

    // 学生授权表
    private final StudentAuthorizationService studentAuthorizationService;





    @Override
    public boolean save(AuthorizationReqVo authorizationReqVo) {
        // 保存
        Authorization po = MapStructs.INSTANCE.reqVo2po(authorizationReqVo);
        return save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorizationVo getAuthorizationByPermission(String permission) {
        LambdaQueryWrapper<Authorization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Authorization::getPermissions, permission);

        Authorization studentPermission = getOne(wrapper);
        if (studentPermission != null) {
            return MapStructs.INSTANCE.po2vo(studentPermission);
        }
        return null;
    }

    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    @Override
    public CourseAndClassVo getCourseAndClasslistByAuth(String token) {
        // 定义返回
        CourseAndClassVo courseAndClassVo = new CourseAndClassVo();

        // 从 Redis 中获取用户权限信息
        AdminUserPermissionDto userPermissionDto = (AdminUserPermissionDto) redises.get(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();
        // 获取权限
        List<Resource> resources = userPermissionDto.getResources();

        // 判断权限，根据权限获取课程和班级信息

        // 课程
        boolean hasQueryCoursePermission = resources.stream()
            .anyMatch(resource -> "course:read".equals(resource.getPermission()));
        // 判断是否有“查询课程权限”
        if (hasQueryCoursePermission) {
            // 获取课程信息
            courseAndClassVo.setCourseList(courseService.getCourseListByCollegeId(users.getCollegeId()));
        } else {
            // 否则通过用户 id 获取课程信息
            courseAndClassVo.setCourseList(courseService.getCourseListByTeacherId(Math.toIntExact(users.getId())));
        }

        // 班级
        // 获取年级信息
        List<GradeVo> grades = gradeService.getGradeInfoByCollegeId(users.getCollegeId());

        // 构造年级及其班级列表的映射
        Map<String, List<ClassVo>> gradList = new HashMap<>();
        for (GradeVo grade : grades) {
            // 获取该年级下的班级信息
            List<ClassVo> classes = classService.getClassInfoByGradeId(grade.getId());
            gradList.put(grade.getName(), classes);
        }
        courseAndClassVo.setGradList(gradList);

        return courseAndClassVo;
    }

    /**
     * 根据用户选择的授权的集合(课程列表和班级列表)生成code
     *
     * @param req   ：AuthCourseAndClassIdReqVo 包含课程id列表和班级id列表的请求体
     * @param token
     * @return ：生成的 code
     */
    @Override
    public String generateSelectionCode(AuthCourseAndClassIdReqVo req, String token) {
        // 从 Redis 中获取用户权限信息
        AdminUserPermissionDto userPermissionDto = redises.getT(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();

        // 生成code
        String code = Strings.getUUID(10);

        // 创建 CodesInfoDto 对象，包含生成者ID和课程+班级信息
        CodesInfoDto codesInfo = new CodesInfoDto();
        codesInfo.setPublisherId(users.getId());
        codesInfo.setCourseAndClassVo(req);

        // 将课程ID和班级ID与code关联并存储在Redis中
        redises.set(Constants.Auth.AUTH_CODE,code, req,Constants.Date.EXPIRE_DATS, TimeUnit.DAYS); // 设置一个合适的过期时间

        // 存到数据库
        codesService.saveCodesInfo(new CodeSaveReqVo(code, Math.toIntExact(users.getId()), req.getCourseIds(), req.getClassIds(),1));
        return code;
    }

    @Override
    public String generateCodeByCourseAndClassInfo(AuthCourseAndClassInfoReqVo reqVo, String token) {
        // 从 Redis 中获取用户权限信息
        AdminUserPermissionDto userPermissionDto = (AdminUserPermissionDto) redises.get(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();

        // 生成code
        String code = UUID.randomUUID().toString();

        // 将课程ID和班级ID与code关联并存储在Redis中
        redises.set(Constants.Auth.AUTH_CODE + code, reqVo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS); // 设置过期时间为7天

        // 将特定的 key 与 code 关联存储到列表中
//        String userKey = Constants.Auth.AuTH_Code_USER + users.getId();
//        redises.lpush(userKey, Constants.Auth.ADMIN_AUTH + code);
//        redises.expire(userKey, Constants.Date.EXPIRE_DAYS, TimeUnit.DAYS); // 设置列表的过期时间

        return code;
    }

//    @Override
//    @Transactional(readOnly = false)
//    public CourseAndClassVo getCourseAndClassByCode(String code, String token) {
//        // 从redis中获取code key对应的value
//        AuthCourseAndClassIdReqVo reqVo = (AuthCourseAndClassIdReqVo) redises.get(Constants.Auth.AUTH_CODE + code);
//
//        // 课程 和 班级
//        String courseIdsStr  = reqVo.getCourseIds();
//        String classIdsStr  = reqVo.getClassIds();
//
//        // 学生用户根据 token 获取用户信息
//
//        StudentInfoDto studentInfoDto = studentService.getStudentByToken(token);
//        // 从studentInfoDto 中取出学生基本信息
//        String wechatId = studentInfoDto.getStudentVo().getWechatId();
//
//
//
//        // 分割课程和班级ID
//        List<Integer> courseIds = Arrays.stream(courseIdsStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//        List<Integer> classIds = Arrays.stream(classIdsStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//
//        // 获取课程信息
//        List<CourseVo> courseList = courseService.getCoursesByIds(courseIds);
//
//        // 获取班级信息
//        List<ClassVo> classList = classService.getClassesByIds(classIds);
//
//        // 获取所有班级的年级ID列表
//        List<Integer> gradeIds = classList.stream().map(ClassVo::getGradeId).collect(Collectors.toList());
//
//        // 获取年级ID与名称的映射
//        Map<Integer, String> gradeNames = gradeService.getGradeNamesByIds(gradeIds);
//
//        // 将班级按年级名称分组
//        Map<String, List<ClassVo>> gradList = classList.stream()
//            .collect(Collectors.groupingBy(classVo -> gradeNames.get(classVo.getGradeId())));
//
//        // 构建返回对象
//        CourseAndClassVo courseAndClassVo = new CourseAndClassVo();
//        courseAndClassVo.setCourseList(courseList);
//        courseAndClassVo.setGradList(gradList);
//
//        return courseAndClassVo;
//    }

    @Override
    @Transactional(readOnly = false)
    public CourseAndClassVo verificationCode(String code, String token) {
        // 检查code的状态
        Short codeStatus = codesService.checkCodeStatus(code);
        if (Objects.equals(codeStatus, Constants.Status.Code_EXIT)) {
            // 如果code在数据库中不存在，抛出异常或者返回错误信息
            JsonVos.raise(CodeMsg.AUTH_CODE_NOT_EXIT);
        }

        if (Objects.equals(codeStatus, Constants.Status.Code_USED)) {
            // 如果code已经被使用，直接返回
            JsonVos.raise(CodeMsg.AUTH_CODE_USED);
        }


        // 从redis中获取code key对应的value
        AuthCourseAndClassIdReqVo reqVo = redises.getT(Constants.Auth.AUTH_CODE + code);

        // 课程 和 班级
        String courseIdsStr = reqVo.getCourseIds();
        String classIdsStr = reqVo.getClassIds();

        // 学生用户根据 token 获取用户信息
        StudentInfoDto studentInfoDto = studentService.getStudentByToken(token);
        // 从studentInfoDto 中取出学生基本信息
        String wechatId = studentInfoDto.getStudentVo().getWechatId();

        // 分割课程和班级ID
        List<Integer> courseIds = Arrays.stream(courseIdsStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> classIds = Arrays.stream(classIdsStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        // 获取课程信息
        List<CourseVo> courseList = courseService.getCoursesByIds(courseIds);

        // 获取班级信息
        List<ClassVo> classList = classService.getClassesByIds(classIds);

        // 获取所有班级的年级ID列表
        List<Integer> gradeIds = classList.stream().map(ClassVo::getGradeId).collect(Collectors.toList());

        // 获取年级ID与名称的映射
        Map<Integer, String> gradeNames = gradeService.getGradeNamesByIds(gradeIds);

        // 将班级按年级名称分组
        Map<String, List<ClassVo>> gradList = classList.stream()
            .collect(Collectors.groupingBy(classVo -> gradeNames.get(classVo.getGradeId())));

        // 获取学生授权表中的现有数据
        StudentAuthorizationVo studentAuthVo = studentAuthorizationService.getStudentAuthorizationById(wechatId);
        StudentAuthorizationReqVo studentAuthReq;
        if (studentAuthVo == null) {
            // 如果学生授权表中没有记录，则插入新记录
            studentAuthReq = new StudentAuthorizationReqVo(wechatId, courseIdsStr, classIdsStr);
            studentAuthorizationService.saveStudentAuthorization(studentAuthReq);
        } else {
            // 如果学生授权表中有记录，则合并新的课程和班级ID
            String existingCourseIdsStr = studentAuthVo.getCourseId();
            String existingClassIdsStr = studentAuthVo.getClassId();

            // 合并课程ID
            Set<String> courseIdSet = new HashSet<>(Arrays.asList(existingCourseIdsStr.split(",")));
            courseIdSet.addAll(Arrays.asList(courseIdsStr.split(",")));
            String mergedCourseIdsStr = String.join(",", courseIdSet);

            // 合并班级ID
            Set<String> classIdSet = new HashSet<>(Arrays.asList(existingClassIdsStr.split(",")));
            classIdSet.addAll(Arrays.asList(classIdsStr.split(",")));
            String mergedClassIdsStr = String.join(",", classIdSet);

            // 更新学生授权表
            studentAuthReq = new StudentAuthorizationReqVo(wechatId, mergedCourseIdsStr, mergedClassIdsStr);
            studentAuthorizationService.updateStudentAuthorization(studentAuthReq);
        }

        // 更新code状态为已使用
        codesService.updateCodeStatus(code, 2);

        // 构建返回对象
        CourseAndClassVo courseAndClassVo = new CourseAndClassVo();
        courseAndClassVo.setCourseList(courseList);
        courseAndClassVo.setGradList(gradList);

        return courseAndClassVo;
    }

}
