package run.hxtia.workbd.pojo.vo.request.save;


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
    @ApiModelProperty(value = "用户邮箱【必须包含@】", required = true)
    private String username;

    @NotNull
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    // TODO: 邮箱验证码

    // TODO: 可以将密钥的盐值生成，让前端来完成
}
