package run.hxtia.workbd.service.UserManagement.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.RoleReqVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

import java.util.Collection;
import java.util.List;

/**
 * 角色模块 业务层
 */
@Transactional(readOnly = true)
public interface RoleService extends IService<Role> {

    /**
     * 角色分页列表
     * @param pageReqVo ：分页参数
     * @param token：用户token
     * @return ：分页后的角色列表
     */
    PageVo<RoleVo> list(RolePageReqVo pageReqVo, String token);

    /**
     * 新建|保存角色
     * @param reqVo ：角色信息
     * @param token ：用户token
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(RoleReqVo reqVo, String token);

    /**
     * 根据用户ID查询角色列表
     * @param userId：用户ID
     * @return ：用户的角色列表
     */
    List<Role> listByUserId(Long userId);

    /**
     * 获取所有组织内角色
     * @param token：用户Token
     * @return ：组织内角色
     */
    List<Role> list(String token);
}
