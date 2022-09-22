package run.hxtia.workbd.service.miniapp.impl;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.mapper.UserMapper;
import run.hxtia.workbd.pojo.dto.UserInfoDto;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;
import run.hxtia.workbd.pojo.vo.response.UserVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.miniapp.WxUserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<UserMapper, User> implements WxUserService {

    private final WxMaService wxMaService;
    private final Redises redises;

    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    @Override
    public String getSessionId(String code) throws Exception {
        MiniApps.checkAppId(wxMaService);
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            String token = UUID.randomUUID().toString().replace("-", "");
            redises.set(Constants.WxMiniApp.TOKEN_PREFIX, token, session, 7, TimeUnit.DAYS);
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
    public UserInfoDto authLogin(WxAuthLoginReqVo wxAuth, String token) {
        // 检查 AppId
        MiniApps.checkAppId(wxMaService);

        // 微信小程序相关的信息
        WxMaUserService userService = wxMaService.getUserService();
        WxMaJscode2SessionResult session = MiniApps.getSession(token);
        String sessionKey = session.getSessionKey();
        String openId = session.getOpenid();
        try {
            // 用户信息校验
            MiniApps.checkUserInfo(
                userService, sessionKey, wxAuth.getRawData(), wxAuth.getSignature()
            );

            // 从redis中读取 user 信息
            UserInfoDto userInfoDto = (UserInfoDto) redises.get(openId);

            // 检查 redis中有无用户
            if (userInfoDto != null) return userInfoDto;

            // 先去数据库查询
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getOpenid, openId);
            User userPo = baseMapper.selectOne(wrapper);
            userInfoDto = new UserInfoDto();

            if (userPo != null) {
                UserVo userVo = new UserVo();

                userInfoDto.setUserVo(MapStructs.INSTANCE.po2vo(userPo));
            } else {
                // 来到这里说明用户是第一次授权，需要注册
                // 解密用户信息并且返回
                WxMaUserInfo userInfo = userService.getUserInfo(
                    sessionKey, wxAuth.getEncryptedData(), wxAuth.getIv()
                );
                userInfoDto.setUserVo(MapStructs.INSTANCE.po2vo(registerUser(userInfo, token)));
            }

            // 将最新消息缓存到 redis
            redises.set(openId, userInfoDto);
            return userInfoDto;

        } finally {
            // 清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

    /**
     * 注册用户【存开发者服务器】
     * @param userInfo：调用微信的接口，返回给
     * @param token：用户令牌
     * @return ：是否成功。
     */
    private User registerUser(WxMaUserInfo userInfo, String token) {
        if (userInfo == null || !StringUtils.hasLength(token)) return null;
        User po = MapStructs.INSTANCE.wxReqVo2po(userInfo);
        po.setOpenid(MiniApps.getOpenId(token));
        if (baseMapper.insert(po) <= 0) {
            return JsonVos.raise(CodeMsg.AUTHORIZED_ERROR);
        }
        return po;
    }
}
