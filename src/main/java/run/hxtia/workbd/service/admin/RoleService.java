package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Role;

/**
 * 角色模块 持久层
 */
@Transactional(readOnly = true)
public interface RoleService extends IService<Role> {
}
