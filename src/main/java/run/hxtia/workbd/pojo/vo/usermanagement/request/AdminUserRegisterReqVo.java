package run.hxtia.workbd.pojo.vo.usermanagement.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【注册】B端超管注册")
public class AdminUserRegisterReqVo {

    @Email
    @NotBlank
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String email;

    @NotNull
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "邮箱验证码", required = true)
    private String code;

}
