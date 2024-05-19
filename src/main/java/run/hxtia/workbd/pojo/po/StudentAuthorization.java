package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 学生授权表，记录学生的授权信息
 */
@Data
@TableName("student_authorizations")
public class StudentAuthorization implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 班级ID
     */
    private String classId;
}
