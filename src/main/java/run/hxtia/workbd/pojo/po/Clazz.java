package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 行政班表(Clazz)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("clazz")
public class Clazz implements Serializable {
    private static final long serialVersionUID = 257594234837037674L;
    /**
     * 班级ID
     */
    private Long id;
    /**
     * 年级ID
     */
    private Integer grade;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 组织ID
     */
    private Short orgId;

}

