package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.CourseMapper;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.vo.request.course.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.request.course.CourseReqVo;
import run.hxtia.workbd.pojo.vo.response.course.CourseVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.notificationwork.CourseService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {



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
    public PageVo<CourseVo> getCourseInfoByCollegeIdWithPagination(Integer collegeId, int pageNum, int pageSize) {
        // 创建分页条件
        Page<Course> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        IPage<Course> coursePage = lambdaQuery().eq(Course::getCollegeId, collegeId).page(page);
        // 将查询结果转换为VO对象
        List<CourseVo> courseVos = coursePage.getRecords().stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
        // 创建并返回分页结果
        PageVo<CourseVo> result = new PageVo<>();
        result.setCount(coursePage.getTotal());
        result.setData(courseVos);
        return result;
    }

}
