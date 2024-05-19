package run.hxtia.workbd.service.notificationwork;

import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.vo.common.request.WxSubscribeMessageReqVo;

@Transactional(readOnly = true)
public interface WxMsgService {
    /**
     * 发送订阅消息的请求
     * @param subscribeMessage：订阅的参数
     * @param token：小程序用户令牌
     * @return ：是否成功
     */
    boolean sendMsg(WxSubscribeMessageReqVo subscribeMessage, String token);
}
