package run.hxtia.workbd.service.UserManagement.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.ResourceDto;
import run.hxtia.workbd.pojo.po.Resource;

import java.util.List;

/**
 * 资源模块 业务层
 */
@Transactional(readOnly = true)
public interface ResourceService extends IService<Resource> {

    /**
     * 根据角色ID构建树状结构的菜单
     * @param roleStrIds：所有角色ID
     * @return ：树状结构的菜单
     */
    List<ResourceDto> listMenu(String roleStrIds);

    /**
     * 构建完整的资源树结构
     * @return ：一整棵父子结构
     */
    List<ResourceDto> listAllTree();

    /**
     * 根据角色IDs获取资源列表
     * @param roleIds：角色IDs
     * @return ：资源列表
     */
    List<Resource> listByRoleIds(List<Short> roleIds);
}
