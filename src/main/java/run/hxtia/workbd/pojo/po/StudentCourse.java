package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 学生课程关联表，记录学生选课信息
 */
@Data
@TableName("student_courses")
public class StudentCourse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 课程ID
     */
    private Integer courseId;
}
