package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.common.validator.BoolNumber;
import run.hxtia.workbd.common.validator.ZOTNumber;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("【编辑】修改作业置顶状态")
public class UserWorkReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "作业ID", required = true)
    private Long workId;

    @BoolNumber
    @ApiModelProperty("是否置顶【1：置顶，0：不置顶】")
    private Short pinStatus;

    @ZOTNumber
    @ApiModelProperty("作业状态【0：未读未完成，1：已读未完成，2：已读已完成】")
    private Short status;

}
