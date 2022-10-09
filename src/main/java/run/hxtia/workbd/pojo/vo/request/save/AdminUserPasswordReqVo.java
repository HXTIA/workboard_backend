package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.request.CaptchaReqVo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("【编辑】用户密码")
public class AdminUserPasswordReqVo extends CaptchaReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
