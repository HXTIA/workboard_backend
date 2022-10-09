package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【修改】忘记密码")
public class AdminUserForgotReqVo {

    @Email
    @NotBlank
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "邮箱验证码", required = true)
    private String code;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
