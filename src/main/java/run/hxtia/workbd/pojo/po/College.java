package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 学院表
 */
@Data
@TableName("colleges")
public class College implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学院ID
     */
    private Integer id;

    /**
     * 学院名称
     */
    private String name;

    /**
     * 学院描述
     */
    private String description;

    /**
     * 学院标志URL
     */
    private String logoUrl;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}

