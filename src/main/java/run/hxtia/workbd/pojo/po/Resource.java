package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 资源表(Resources)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("resources")
public class Resource implements Serializable {
    private static final long serialVersionUID = 259669503490449319L;
    /**
     * 资源ID
     */
    private Integer id;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 映射路由
     */
    private String uri;
    /**
     * 具体权限
     */
    private String permission;
    /**
     * 资源类型【1目录，2：菜单，3：资源】
     */
    private Short type;
    /**
     * 序号
     */
    private Short sn;
    /**
     * 图标
     */
    private String icon;
    /**
     * 父级资源【0：无父资源】
     */
    private Integer parentId;

}

