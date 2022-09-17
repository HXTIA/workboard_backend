package run.hxtia.workbd.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.LoginReqVo;
import run.hxtia.workbd.pojo.vo.result.LoginVo;

@Transactional(readOnly = true)
public interface UserService extends IService<User> {

    // 用户登录
    @Transactional(readOnly = false)
    LoginVo login(LoginReqVo reqVo);

}
