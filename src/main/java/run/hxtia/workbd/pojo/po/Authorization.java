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
     * 权限描述
     */
    private String permissions;

    /**
     * 审核者ID
     */
    private Integer publicId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
