package run.hxtia.workbd.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户作业信息")
public class UserWorkVo extends WorkVo {

    @ApiModelProperty("作业状态【0：未读未完成，1：已读未完成，2：已读已完成】")
    private Short status;

    @ApiModelProperty("是否置顶【0：不置顶，1：置顶】")
    private Short pin;

}

