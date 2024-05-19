package run.hxtia.workbd.common.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.pojo.vo.common.response.WxTokenVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;

/**
 * 小程序工具类
 */
public class MiniApps {
    /**
     * 小程序ID
     */
    public final static String APP_ID = WorkBoardProperties.getNotBeanWx().getAppId();
    /**
     * 小程序密钥
     */
    public final static String SECRET = WorkBoardProperties.getNotBeanWx().getSecret();
    /**
     * 小程序模板ID
     */
    public final static String TEMPLATE_ID = WorkBoardProperties.getNotBeanWx().getTemplateId();

    /**
     * 拿到 Redis 工具类
     */
    private static final Redises REDISES = Redises.getRedises();

    public static String buildGetTokenUrl(String code) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.WxApp.PREFIX)
            .append(Constants.WxApp.GET_TOKEN)
            .append("?appid=")
            .append(APP_ID)
            .append("&secret=")
            .append(SECRET)
            .append("&js_code=")
            .append(code)
            .append("&grant_type=authorization_code");

        return url.toString();
    }

    public static String buildGetWXAccessTokenUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.WxApp.PREFIX)
            .append(Constants.WxApp.WX_ACCESS_TOKEN)
            .append("?grant_type=client_credential")
            .append("&appid=")
            .append(APP_ID)
            .append("&secret=")
            .append(SECRET);

        return url.toString();
    }

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
        return getSessionKey(Constants.WxMiniApp.WX_PREFIX, token);
    }

    /**
     * 根据Token获取 OpenId
     * @param token：令牌
     * @return ：OpenId
     */
    public static String getOpenId(String token) {
        return getOpenId(Constants.WxMiniApp.WX_PREFIX, token);
    }

    /**
     * 根据Token获取 Session
     * @param token：令牌
     * @return ：Session
     */
    public static WxTokenVo getWxTokenVo(String token) {
        return getWxTokenVo(Constants.WxMiniApp.WX_PREFIX , token);
    }

    /**
     * 根据Token 获取 SessionKey
     * @param prefix：前缀
     * @param token：令牌
     * @return ：SessionKey
     */
    public static String getSessionKey(String prefix, String token) {
        WxTokenVo tokenVo = getWxTokenVo(prefix, token);
        if (tokenVo == null) return null;
        return  tokenVo.getSessionKey();
    }

    /**
     * 根据Token 获取 OpenId
     * @param prefix：前缀
     * @param token：令牌
     * @return ：OpenId
     */
    public static String getOpenId(String prefix, String token) {
        WxTokenVo tokenVo = getWxTokenVo(prefix, token);
        if (tokenVo == null) return null;
        return tokenVo.getOpenid();
    }

    /**
     * 根据Token 获取 Session
     * @param prefix：前缀
     * @param token：令牌
     * @return ：Session
     */
    public static WxTokenVo getWxTokenVo(String prefix, String token) {
        if (!StringUtils.hasLength(token)) return null;
        return (WxTokenVo) REDISES.get(prefix + token);
    }

}
