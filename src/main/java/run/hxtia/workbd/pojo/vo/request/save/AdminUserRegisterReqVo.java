package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【注册】B端超管注册")
public class AdminUserRegisterReqVo {

    @Email
    @ApiModelProperty(value = "用户邮箱【必须包含@】", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

    // TODO: 邮箱验证码
}
