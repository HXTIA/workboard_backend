package run.hxtia.workbd.service.miniapp;


import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.UserInfoDto;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;

@Transactional(readOnly = true)
public interface WxUserService extends IService<User> {

    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    String getSessionId(String code) throws Exception;

    /**
     * 认证登录信息
     * @param wxAuth：认证参数
     * @return ：解密后的参数
     */
    @Transactional(readOnly = false)
    UserInfoDto authLogin(WxAuthLoginReqVo wxAuth, String token);
}
