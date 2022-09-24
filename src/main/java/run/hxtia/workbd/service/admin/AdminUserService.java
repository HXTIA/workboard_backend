package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserRegisterReqVo;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;

@Transactional(readOnly = true)
public interface AdminUserService extends IService<AdminUsers> {


    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     */
    AdminLoginVo login(AdminLoginReqVo reqVo);

    /**
     * 超管注册
     * @param reqVo：注册的信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean register(AdminUserRegisterReqVo reqVo);

}
