package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.AdminUserRole;

import java.util.List;

@Transactional(readOnly = true)
public interface AdminUserRoleService extends IService<AdminUserRole> {

    /**
     * 根据用户ID，删除所有的角色信息
     * @param userId：用户ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByUserId(Long userId);

    /**
     * 添加角色信息
     * @param userId：用户ID
     * @param roleIdsStr：角色ID【多个角色用 逗号, 隔开】
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(Long userId, String roleIdsStr);

    /**
     * 根据用户ID获取角色ID
     * @param userId：用户ID
     * @return ：该用户的所有角色ID
     */
    List<Short> listRoleIds(Integer userId);

    /**
     * 根据角色ID获取用户ID
     * @param roleId：用户ID
     * @return ：该角色的所有用户ID
     */
    List<Long> listAdminUserIds(Short roleId);

}
