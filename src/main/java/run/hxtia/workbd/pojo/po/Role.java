package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表(Roles)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 912707195842334697L;
    /**
     * 角色ID
     */
    private Short id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色简介
     */
    private String intro;
    /**
     * 学院 ID
     */
    private Integer collegeId;

}

