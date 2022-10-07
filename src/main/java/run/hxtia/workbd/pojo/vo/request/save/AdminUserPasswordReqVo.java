package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【编辑】用户密码")
public class AdminUserPasswordReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
