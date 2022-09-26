package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.RoleResource;

/**
 * 角色 - 资源 业务层
 */
@Transactional(readOnly = true)
public interface RoleResourceService extends IService<RoleResource> {

}
