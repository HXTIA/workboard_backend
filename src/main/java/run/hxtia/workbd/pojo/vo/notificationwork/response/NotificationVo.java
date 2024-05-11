package run.hxtia.workbd.pojo.vo.notificationwork.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("通知信息")
public class NotificationVo {
    @ApiModelProperty("通知ID")
    private Integer id;

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String message;

    @ApiModelProperty("通知类型")
    private String  type;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}


