package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.RoleResource;

import java.util.List;

/**
 * 角色 - 资源 业务层
 */
@Transactional(readOnly = true)
public interface RoleResourceService extends IService<RoleResource> {

    /**
     * 根据角色ID查询所有的资源
     * @param roleIds ：角色ID
     * @return ：拥有资源的并集
     */
    List<Short> listResourceIds(List<Short> roleIds);
}
