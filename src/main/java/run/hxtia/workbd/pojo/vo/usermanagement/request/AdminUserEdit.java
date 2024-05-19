package run.hxtia.workbd.pojo.vo.usermanagement.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@ApiModel("【编辑】用户信息")
public class AdminUserEdit {

    @NotNull
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

}
