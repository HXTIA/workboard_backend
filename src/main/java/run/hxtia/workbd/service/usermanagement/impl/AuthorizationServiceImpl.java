package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.mapper.AuthorizationMapper;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Authorization;
import run.hxtia.workbd.pojo.po.Resource;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CourseAndClassByAuthReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */


@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, Authorization> implements AuthorizationService {
    // Redis
    private final Redises redises;

    // 课程
    private final CourseService courseService;

    // 年级
    private final GradeService gradeService;
    // 班级
    private final ClassService classService;

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
}
