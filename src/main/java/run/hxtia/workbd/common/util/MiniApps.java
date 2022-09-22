package run.hxtia.workbd.common.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;

import java.util.Objects;

/**
 * 小程序工具类
 */
public class MiniApps {
    /**
     * 小程序ID
     */
    public final static String APP_ID = WorkBoardProperties.getNotBeanWx().getAppId();
    /**
     * 小程序模板ID
     */
    public final static String TEMPLATE_ID = WorkBoardProperties.getNotBeanWx().getTemplateId();

    /**
     * 拿到 Redis 工具类
     */
    private static final Redises REDISES = Redises.getRedises();

    /**
     * 检查appId
     */
    public static void checkAppId(WxMaService wxMaService) {
        if (!wxMaService.switchover(APP_ID)) {
            JsonVos.raise(CodeMsg.NO_APP_ID);
        }
    }

    /**
     * 检查用户信息
     * @param sessionKey ：sessionKey
     * @param rawData ：原始数据
     * @param signature ：签名密钥
     */
    public static void checkUserInfo(WxMaUserService userService,
                                     String sessionKey,
                                     String rawData,
                                     String signature) {
        if (!userService.checkUserInfo(sessionKey, rawData, signature)) {
            JsonVos.raise(CodeMsg.USER_INFO_ERROR);
        }
    }

    /**
     * 根据Token获取 SessionKey
     * @param token：令牌
     * @return ：SessionKey
     */
    public static String getSessionKey(String token) {
        return getSessionKey(Constants.WxMiniApp.TOKEN_PREFIX, token);
    }
    /**
     * 根据Token获取 OpenId
     * @param token：令牌
     * @return ：OpenId
     */
    public static String getOpenId(String token) {
        return getOpenId(Constants.WxMiniApp.TOKEN_PREFIX, token);
    }

    /**
     * 根据Token获取 Session
     * @param token：令牌
     * @return ：Session
     */
    public static WxMaJscode2SessionResult getSession(String token) {
        return getSession(Constants.WxMiniApp.TOKEN_PREFIX , token);
    }

    /**
     * 根据Token 获取 SessionKey
     * @param prefix：前缀
     * @param token：令牌
     * @return ：SessionKey
     */
    public static String getSessionKey(String prefix, String token) {
        return Objects.requireNonNull(getSession(prefix, token)).getSessionKey();
    }

    /**
     * 根据Token 获取 OpenId
     * @param prefix：前缀
     * @param token：令牌
     * @return ：OpenId
     */
    public static String getOpenId(String prefix, String token) {
        return Objects.requireNonNull(getSession(prefix, token)).getOpenid();
    }

    /**
     * 根据Token 获取 Session
     * @param prefix：前缀
     * @param token：令牌
     * @return ：Session
     */
    public static WxMaJscode2SessionResult getSession(String prefix, String token) {
        if (!StringUtils.hasLength(token)) return null;
        return (WxMaJscode2SessionResult) REDISES.get(prefix + token);
    }

}
