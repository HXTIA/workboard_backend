package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

import java.util.List;

/**
 * 角色模块 持久层
 */
@Transactional(readOnly = true)
public interface RoleService extends IService<Role> {

    /**
     * 角色分页列表
     * @param pageReqVo：分页参数
     * @return ：分页后的角色列表
     */
    PageVo<RoleVo> list(RolePageReqVo pageReqVo);
}
