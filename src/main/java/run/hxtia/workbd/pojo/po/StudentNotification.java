package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 学生通知表，记录向学生发送的通知
 */
@Data
@TableName("student_notifications")
public class StudentNotification implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 通知ID
     */
    private Integer notificationId;
}
