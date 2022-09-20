package run.hxtia.workbd.services.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaSubscribeService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;

@Transactional(readOnly = true)
public interface WxMsgService {
    boolean sendMsg(WxSubscribeMessageReqVo subscribeMessage, String token);
}
