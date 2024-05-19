package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 行政班表(Classes)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("classes")
public class Classes implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    private Integer id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 年级ID
     */
    private Integer gradeId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

}

