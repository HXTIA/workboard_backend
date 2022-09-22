package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签表(Tags)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("tags")
public class Tag implements Serializable {
    private static final long serialVersionUID = -85033188629026485L;
    /**
     * 标签ID
     */
    private Integer id;
    /**
     * 标签细节
     */
    private String detail;

}

