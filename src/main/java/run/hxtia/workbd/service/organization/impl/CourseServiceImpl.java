package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.CourseMapper;
import run.hxtia.workbd.mapper.StudentCourseMapper;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCourse;
import run.hxtia.workbd.pojo.vo.request.organization.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.CourseReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.CourseVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.organization.CourseService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

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

    @Override
    @Transactional(readOnly = false)
    public boolean saveStudentCourse(Integer studentId, Integer courseId) {
        // 创建一个QueryWrapper对象
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("student_id", studentId).eq("course_id", courseId);
        // 检查学生是否已经选过这门课
        boolean exists = studentCourseMapper.selectOne(queryWrapper) != null;
        if (exists) {
            // 如果学生已经选过这门课，那么返回false，表示选课操作失败
            return false;
        }

        // 如果学生没有选过这门课，那么创建新的选课记录
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        // 使用StudentCourseMapper的insert方法保存StudentCourse对象到数据库
        int result = studentCourseMapper.insert(studentCourse);
        // 如果插入操作成功，返回true，否则返回false
        return result > 0;

    }
}
