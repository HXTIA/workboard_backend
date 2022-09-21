package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.entity.User;
import run.hxtia.workbd.pojo.vo.request.LoginReqVo;
import run.hxtia.workbd.pojo.vo.result.LoginVo;

@Transactional(readOnly = true)
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     */
    @Transactional(readOnly = false)
    LoginVo login(LoginReqVo reqVo);

}
