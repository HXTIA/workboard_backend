package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知表
 */
@Data
@TableName("notifications")
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 状态
     */
    private String status;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
