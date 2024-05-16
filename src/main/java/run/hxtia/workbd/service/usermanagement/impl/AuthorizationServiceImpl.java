package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.mapper.AuthorizationMapper;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Authorization;
import run.hxtia.workbd.pojo.po.Resource;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassIdReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassInfoReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        AdminUserPermissionDto userPermissionDto = (AdminUserPermissionDto) redises.get(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();

        // 生成code
        String code = UUID.randomUUID().toString();

        // 将课程ID和班级ID与code关联并存储在Redis中
        redises.set(Constants.Auth.ADMIN_AUTH,code, req,Constants.Date.EXPIRE_DATS, TimeUnit.DAYS); // 设置一个合适的过期时间

        // 将特定的 key 与 code 关联存储
        redises.set(Constants.Auth.AuTH_Code_USER, String.valueOf(users.getId()), Constants.Auth.ADMIN_AUTH + code);

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
        redises.set(Constants.Auth.ADMIN_AUTH + code, reqVo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS); // 设置过期时间为7天

        // 将特定的 key 与 code 关联存储到列表中
//        String userKey = Constants.Auth.AuTH_Code_USER + users.getId();
//        redises.lpush(userKey, Constants.Auth.ADMIN_AUTH + code);
//        redises.expire(userKey, Constants.Date.EXPIRE_DAYS, TimeUnit.DAYS); // 设置列表的过期时间

        return code;
    }
}
