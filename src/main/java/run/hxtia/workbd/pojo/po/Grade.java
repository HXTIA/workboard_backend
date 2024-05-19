package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 年级表
 */
@Data
@TableName("grades")
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 年级ID
     */
    private Integer id;

    /**
     * 年级名称
     */
    private String name;

    /**
     * 所属学院ID
     */
    private Integer collegeId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
