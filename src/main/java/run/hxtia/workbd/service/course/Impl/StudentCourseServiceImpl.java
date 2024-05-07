package run.hxtia.workbd.service.course.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.StudentCourseMapper;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCourse;
import run.hxtia.workbd.pojo.vo.request.course.StudentCourseReqVo;
import run.hxtia.workbd.pojo.vo.response.course.CourseVo;
import run.hxtia.workbd.service.course.StudentCourseService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {


    private final StudentCourseMapper studentCourseMapper;

    /**
     * 添加学生课程
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean save(StudentCourseReqVo reqVo) {
        // 将请求对象转换为实体对象
        StudentCourse studentCourse = MapStructs.INSTANCE.reqVo2po(reqVo);
        // 使用Mapper的insert方法保存到数据库
        return save(studentCourse);
    }

    /**
     * 更新学生课程信息
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean update(StudentCourseReqVo reqVo) {
        // 将请求对象转换为实体对象
        StudentCourse studentCourse = MapStructs.INSTANCE.reqVo2po(reqVo);
        // 使用Mapper的update方法更新数据库
        return updateById(studentCourse);
    }

    /**
     * 删除学生课程
     * @param studentId ：学生ID
     * @param courseId ：课程ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    public boolean delete(Integer studentId, Integer courseId) {
        // 使用lambdaUpdate方法从数据库中删除特定的学生课程记录
        boolean removed = lambdaUpdate().eq(StudentCourse::getStudentId, studentId).eq(StudentCourse::getCourseId, courseId).remove();
        return removed;
    }

    /**
     * 检查学生课程是否存在
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 如果学生课程存在，返回true，否则返回false
     */
    @Override
    public boolean exists(Integer studentId, Integer courseId) {
        // 使用lambdaQuery方法查询数据库
        return lambdaQuery().eq(StudentCourse::getStudentId, studentId).eq(StudentCourse::getCourseId, courseId).one() != null;
    }

    /**
     * 根据学生ID获取学生课程信息
     * @param studentId ：学生ID
     * @return 学生课程信息列表
     */
    @Override
    public List<CourseVo> getStudentCoursesByStudentId(Integer studentId) {
        // 使用StudentCourseMapper的selectCoursesByStudentId方法获取课程信息
        List<Course> courses = studentCourseMapper.selectCoursesByStudentId(studentId);
        // 使用MapStructs.INSTANCE.po2vo方法将课程信息转换为视图对象
        return courses.stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
    }
}
