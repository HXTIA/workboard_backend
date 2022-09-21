package run.hxtia.workbd.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色-资源表(RolesResources)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
public class RolesResources implements Serializable {
    private static final long serialVersionUID = -16785195545967265L;
    /**
     * 角色ID
     */
    private Short roleId;
    /**
     * 资源ID
     */
    private Short resourceId;

}

