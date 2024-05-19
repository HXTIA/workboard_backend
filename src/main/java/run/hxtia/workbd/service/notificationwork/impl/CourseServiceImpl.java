package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.mapper.CourseMapper;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final StudentCourseService studentCourseService;


    @Override
    @Transactional(readOnly = false)
    public boolean save(CourseReqVo reqVo) {
        // 检查课程名称是否已经存在
        boolean exists = lambdaQuery().eq(Course::getName, reqVo.getName()).eq(Course::getCollegeId, reqVo.getCollegeId()).one() != null;
        if (exists) {
            // 如果课程名称已经存在，那么返回false，表示保存操作失败
            return false;
        }

        // 如果课程名称不存在，那么创建新的课程
        Course course = MapStructs.INSTANCE.reqVo2po(reqVo);
        // 使用save方法保存Course对象到数据库
        return save(course);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(CourseEditReqVo reqVo) {
        // 检查在同一个学院下是否已经存在相同的课程名称
        boolean exists = lambdaQuery().eq(Course::getName, reqVo.getName()).eq(Course::getCollegeId, reqVo.getCollegeId()).one() != null;
        if (exists) {
            // 如果存在，那么返回false，表示更新操作失败
            return false;
        }

        // 如果不存在，那么更新课程信息
        Course course = MapStructs.INSTANCE.reqVo2po(reqVo);
        return updateById(course);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Integer courseId) {
        // 使用removeById方法从数据库中删除Course对象
        studentCourseService.deleteByCourseId(courseId);
        return removeById(courseId);
    }

    @Override
    public CourseVo getCourseInfoById(Integer courseId) {
        // 使用getById方法从数据库中获取Course对象
        Course course = getById(courseId);
        // 将Course对象转换为CourseVo对象
        return MapStructs.INSTANCE.po2vo(course);
    }

    @Override
    public PageVo<CourseVo> getList() {
        // 使用list方法从数据库中获取所有的Course对象
        List<Course> courses = list();
        // 将Course对象列表转换为CourseVo对象列表
        List<CourseVo> courseVos = courses.stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
        // 创建并返回分页结果
        PageVo<CourseVo> result = new PageVo<>();
        result.setCount((long) courseVos.size());
        result.setData(courseVos);
        return result;
    }

    @Override
    public boolean checkCourseInfo(Integer courseId) {
        // 使用getById方法从数据库中获取Course对象
        Course course = getById(courseId);
        // 如果Course对象存在，返回true，否则返回false
        return course != null;
    }

    @Override
    public boolean checkCourseExists(String courseName, Integer collegeId) {
        // 检查名称是否已经存在
        boolean exists = lambdaQuery().eq(Course::getName, courseName).eq(Course::getCollegeId, collegeId).one() != null;
        return exists;
    }

    @Override
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> courseIds = Arrays.asList(ids.split(","));
        // 检查是否能删除
//        checkNotificationRemove(notificationIds);
        boolean deleteCourse = removeByIds(courseIds);
        boolean deleteStudentCourse = studentCourseService.removeByCourseId(courseIds);
        // 在用户作业表里删除通知
        if (!deleteCourse || !deleteStudentCourse) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }

    @Override
    public PageVo<CourseVo> getPage(CoursePageReqVo pageReqVo) {
        // 构建查询条件
        MpLambdaQueryWrapper<Course> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Course::getName, Course::getDescription).
            eq(Course::getCollegeId, pageReqVo.getCollegeId());

        // 构建分页结果
        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper)
            .buildVo(MapStructs.INSTANCE::po2vo);
    }

    @Override
    public List<CourseVo> getCourseListByCollegeId(Integer collegeId) {
        // 构建查询条件
        MpLambdaQueryWrapper<Course> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Course::getCollegeId, collegeId);

        // 查询结果
        List<Course> courses = list(wrapper);
        // 将Course对象列表转换为CourseVo对象列表
        return courses.stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
    }

    @Override
    public List<CourseVo> getCourseListByTeacherId(Integer teacherId) {
        // 构建查询条件
        MpLambdaQueryWrapper<Course> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Course::getTeacherId, teacherId);

        // 查询结果
        List<Course> courses = list(wrapper);
        // 将Course对象列表转换为CourseVo对象列表
        return courses.stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseVo> getCoursesByIds(List<Integer> courseIds) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Course::getId, courseIds);
        List<Course> courses = list(wrapper);
        return courses.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }
}
