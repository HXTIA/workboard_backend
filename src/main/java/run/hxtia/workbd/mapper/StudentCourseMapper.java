package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCourse;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Repository
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @Select("SELECT c.* FROM student_courses sc JOIN courses c ON sc.course_id = c.id WHERE sc.student_id = #{studentId}")
    List<Course> selectCoursesByStudentId(@Param("studentId") String studentId);

    @Select("SELECT c.* FROM student_courses sc JOIN courses c ON sc.course_id = c.id WHERE sc.student_id = #{studentId}")
    IPage<Course> selectCoursesByStudentIdWithPagination(Page<StudentCourse> page, @Param("studentId") String studentId);
}
