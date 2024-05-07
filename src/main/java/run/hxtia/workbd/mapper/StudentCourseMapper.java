package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCourse;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @Select("SELECT c.* FROM student_course sc JOIN course c ON sc.course_id = c.id WHERE sc.student_id = #{studentId}")
    List<Course> selectCoursesByStudentId(@Param("studentId") Integer studentId);
}
