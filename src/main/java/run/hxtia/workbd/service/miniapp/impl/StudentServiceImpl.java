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
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.pojo.dto.UserInfoDto;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.UserAvatarReqVo;
import run.hxtia.workbd.pojo.vo.request.save.UserReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.miniapp.StudentService;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

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
            String token = Strings.getUUID();
            redises.set(Constants.WxMiniApp.WX_PREFIX, token, session, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);
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
            UserInfoDto userInfoDto = (UserInfoDto) redises.get(Constants.WxMiniApp.WX_USER, openId);

            // 检查 redis中有无用户
            if (userInfoDto != null) return userInfoDto;

            // 先去数据库查询
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getWechatId, openId);
            Student studentPo = baseMapper.selectOne(wrapper);
            userInfoDto = new UserInfoDto();

            if (studentPo != null) {
                userInfoDto.setUserVo(MapStructs.INSTANCE.po2vo(studentPo));
            } else {
                // 来到这里说明用户是第一次授权，需要注册
                // 解密用户信息并且返回
                WxMaUserInfo userInfo = userService.getUserInfo(
                    sessionKey, wxAuth.getEncryptedData(), wxAuth.getIv()
                );
                userInfoDto.setUserVo(MapStructs.INSTANCE.po2vo(registerUser(userInfo, token)));
            }

            // 将最新消息缓存到 redis
            redises.set(Constants.WxMiniApp.WX_USER, openId, userInfoDto);
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
    private Student registerUser(WxMaUserInfo userInfo, String token) {
        if (userInfo == null || !StringUtils.hasLength(token)) return null;
        Student po = MapStructs.INSTANCE.wxReqVo2po(userInfo);
        po.setWechatId(MiniApps.getOpenId(token));
        if (baseMapper.insert(po) <= 0) {
            return JsonVos.raise(CodeMsg.AUTHORIZED_ERROR);
        }
        return po;
    }

    /**
     * 完善用户信息
     * @param reqVo：用户信息
     * @return ：是否成功
     */
    @Override
    public boolean update(UserReqVo reqVo) {
        Student po = MapStructs.INSTANCE.reqVo2po(reqVo);
        // TODO：判断组织是否存在
//        if (!orgService.isExist(reqVo.getOrgId())) {
//            return JsonVos.raise(CodeMsg.NO_ORG_INFO);
//        }

        return baseMapper.updateById(po) > 0;
    }

    /**
     * 用户上传头像
     * @param reqVo：头像数据
     * @return ：是否成功
     */
    @Override
    public boolean update(UserAvatarReqVo reqVo) throws Exception {
        Student po = new Student();
        po.setId(reqVo.getId());
        return Uploads.uploadOneWithPo(po,
            new UploadReqParam(reqVo.getAvatarUrl(),
                reqVo.getAvatarFile()),
            baseMapper, Student::setAvatarUrl);
    }
}
