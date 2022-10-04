package run.hxtia.workbd.pojo.dto;

import lombok.Data;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Resource;
import run.hxtia.workbd.pojo.po.Role;

import java.util.List;

/**
 * 用于添加权限的类 【用户信息 + 角色信息 + 权限信息（资源）】
 */
@Data
public class AdminUserPermissionDto {

    /**
     * 用户信息
     */
    private AdminUsers users;
    /**
     * 角色信息
     */
    private List<Role> roles;
    /**
     * 资源信息
     */
    private List<Resource> resources;

}
