package run.hxtia.workbd.services.miniapp.impl;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.redis.RedisUtil;
import run.hxtia.workbd.common.utils.JsonVos;
import run.hxtia.workbd.mappers.UserMapper;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.services.miniapp.WxUserService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<UserMapper, User> implements WxUserService {

    private final WxMaService wxMaService;
    private final RedisUtil redisUtil;

    @Value("${work-board.wx.app-id}")
    private String appId;

    @Override
    public String getSessionId(String code) throws Exception {
        checkAppId();
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            //TODO: 将session放入redis中
            String token = UUID.randomUUID().toString().replace("-", "");
            token = "wx:" + token;
            redisUtil.set("user:" + token, session);
            Caches.putToken(token, session);
            return  token;
        } finally {
            //清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

    /**
     * 获取用户信息
     * @param wxAuth：认证时所需要的参数
     * @return ：用户信息
     */
    @Override
    public WxMaUserInfo authLogin(WxAuthLoginReqVo wxAuth) {
        checkAppId();
        WxMaUserService userService = wxMaService.getUserService();
        //TODO：从缓存中取出session
        WxMaJscode2SessionResult session = (WxMaJscode2SessionResult) redisUtil.get("user:" + wxAuth.getToken());;
        String sessionKey = session.getSessionKey();
        try {
            // 用户信息校验
            if (!userService.checkUserInfo(sessionKey, wxAuth.getRawData(), wxAuth.getSignature()))
                return JsonVos.raise(CodeMsg.USER_INFO_FAIL);
            // 解密用户信息并且返回
            WxMaUserInfo userInfo = userService.getUserInfo(
                sessionKey, wxAuth.getEncryptedData(), wxAuth.getIv()
            );
            return userInfo;
        } finally {
            // 清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

    /**
     * 检查appId
     */
    private void checkAppId() {
        if (!wxMaService.switchover(appId)) {
            JsonVos.raise(CodeMsg.NO_APP_ID);
        }
    }

}
