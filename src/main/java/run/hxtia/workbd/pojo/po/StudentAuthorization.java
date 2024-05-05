package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
    private Integer studentId;

    /**
     * 授权ID
     */
    private Integer authorizationId;
}
