package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.organization.response.OrganizationVo;

@Repository
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT " +
        "st.wechat_id AS studentId, " +
        "cl.id AS collegeId, cl.name AS collegeName, cl.logo_url AS collegeLogoUrl, " +
        "gr.id AS gradeId, gr.name AS gradeName, gr.college_id AS gradeCollegeId, " +
        "cls.id AS classId, cls.name AS className, cls.grade_id AS classGradeId " +
        "FROM students st " +
        "JOIN colleges cl ON st.college_id = cl.id " +
        "JOIN grades gr ON st.grade_id = gr.id " +
        "JOIN classes cls ON st.class_id = cls.id " +
        "WHERE st.wechat_id = #{studentId}")
    @Results({
        @Result(property = "college.id", column = "collegeId"),
        @Result(property = "college.name", column = "collegeName"),
        @Result(property = "college.logoUrl", column = "collegeLogoUrl"),
        @Result(property = "grade.id", column = "gradeId"),
        @Result(property = "grade.name", column = "gradeName"),
        @Result(property = "grade.collegeId", column = "gradeCollegeId"),
        @Result(property = "classVo.id", column = "classId"),
        @Result(property = "classVo.name", column = "className"),
        @Result(property = "classVo.gradeId", column = "classGradeId")
    })
    OrganizationVo getOrganizationDetailsByStudentId(@Param("studentId") String studentId);
}
