package run.hxtia.workbd.pojo.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("验证码请求对象")
public class CaptchaReqVo {
    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;

    @NotBlank
    @ApiModelProperty(value = "生成验证码时的key", required = true)
    private String verifyKey;
}
