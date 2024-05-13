package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;

@Data
@ApiModel("【创建】通知信息")
public class NotificationReqVo {
    @ApiModelProperty("通知ID【没有ID就是新建，ID > 0 是编辑】")
    private String notification_id;

    @NotNull
    @ApiModelProperty(value = "发布状态", required = true)
    private String status;

    @NotNull
    @ApiModelProperty(value = "通知标题", required = true)
    private String title;

    @NotNull
    @ApiModelProperty(value = "通知内容", required = true)
    private String content;

    @NotNull
    @ApiModelProperty(value = "接收者类型（用户1、班级2等）", required = true)
    private String receiver_type;

    @NotNull
    @ApiModelProperty(value = "接收者ID列表", required = true)
    private Array receiver_ids;

    @NotNull
    @ApiModelProperty(value = " 预定发布时间（可选）", required = true)
    private String scheduled_time;
}
