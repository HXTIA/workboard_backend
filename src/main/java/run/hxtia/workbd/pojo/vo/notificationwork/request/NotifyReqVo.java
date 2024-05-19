package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */


@Data
@AllArgsConstructor
@ApiModel(" 通知对象 ")
public class NotifyReqVo {

    /**
     * 标题
     */
    @ApiModelProperty("通知标题")
    private String title;

    /**
     * 消息内容
     */
    @ApiModelProperty("通知内容")
    private String message;

    /**
     * 用户ID
     */
    @ApiModelProperty("通知发布者ID")
    private String userId;

    /**
     * 状态
     */
    @ApiModelProperty("通知状态")
    private String status;

    /**
     * 类型
     */
    @ApiModelProperty("通知类型")
    private String type;
}
