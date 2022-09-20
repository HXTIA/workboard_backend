package run.hxtia.workbd.pojo.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("【登录】后台模块")
public class LoginReqVo {

    @NotBlank
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
