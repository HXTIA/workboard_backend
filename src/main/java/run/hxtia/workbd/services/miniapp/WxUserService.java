package run.hxtia.workbd.services.miniapp;


import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;

@Transactional(readOnly = true)
public interface WxUserService extends IService<User> {

    String getSessionId(String code) throws Exception;

    WxMaUserInfo authLogin(WxAuthLoginReqVo wxAuth);
}
