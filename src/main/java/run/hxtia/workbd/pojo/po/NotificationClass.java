package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 通知班级关联表
 */
@Data
@TableName("notification_classes")
public class NotificationClass implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    private Integer notificationId;

    /**
     * 班级ID
     */
    private Integer classId;
}
