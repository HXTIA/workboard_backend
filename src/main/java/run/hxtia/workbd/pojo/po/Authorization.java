package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 授权表
 */
@Data
@TableName("authorizations")
public class Authorization implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 授权ID
     */
    private Integer id;

    /**
     * 申请者ID
     */
    private Integer applicantId;

    /**
     * 权限描述
     */
    private String permissions;

    /**
     * 授权原因
     */
    private String reason;

    /**
     * 授权状态
     */
    private String status;

    /**
     * 审核者ID
     */
    private Integer reviewerId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
