package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */

@Data
@AllArgsConstructor
@ApiModel("【发布】发布通知请求对象")
public class NotificationPublishReqVo {

    @ApiModelProperty("通知info")
    private NotifyReqVo notification;

    @ApiModelProperty("通知类型")
    private String type;

    @ApiModelProperty("发给谁？")
    private NotificationReqTos reqTos;

}
