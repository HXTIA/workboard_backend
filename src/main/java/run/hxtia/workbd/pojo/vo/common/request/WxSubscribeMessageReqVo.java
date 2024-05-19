package run.hxtia.workbd.pojo.vo.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("【消息】微信消息订阅")
public class WxSubscribeMessageReqVo {

    @ApiModelProperty(value = "用户的openId", hidden = true)
    private String toUser;

    @ApiModelProperty(value = "模板Id【不传就是默认的】")
    private String templateId;

    @ApiModelProperty(value = "点击模板卡片后的跳转页面，仅限本小程序内的页面【示例index?foo=bar】")
    private String page;

    @ApiModelProperty(value = "模板内容", required = true)
    private List<MsgData> data;

    @ApiModelProperty(value = "跳转小程序类型【默认：developer】")
    private String miniprogramState = "developer";

    @ApiModelProperty(value = "进入小程序查看”的语言类型【默认：zh_CN】")
    private String lang = "zh_CN";

    @Data
    public static class MsgData {

        @ApiModelProperty(value = "key键名", required = true)
        private String name;

        @ApiModelProperty(value = "value值", required = true)
        private String value;
    }

}
