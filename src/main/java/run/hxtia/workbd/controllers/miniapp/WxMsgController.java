package run.hxtia.workbd.controllers.miniapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.utils.JsonVos;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.services.miniapp.WxMsgService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/messages")
@RequiredArgsConstructor
@Api(tags = "WxMsgController")
@Tag(name = "WxMsgController", description = "小程序消息模块")
public class WxMsgController {

    private final WxMsgService msgService;

    @PostMapping("/send")
    @ApiOperation("订阅消息")
    public JsonVo send(@RequestBody WxSubscribeMessageReqVo subscribeMessage, HttpServletRequest request) {
        if (msgService.sendMsg(subscribeMessage, request.getHeader("Token"))) {
            return JsonVos.ok(CodeMsg.SUB_MSG_OK);
        } else {
            return JsonVos.error(CodeMsg.SUB_MSG_ERROR);
        }
    }
}
