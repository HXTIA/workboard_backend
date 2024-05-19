package run.hxtia.workbd.pojo.vo.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel("验证码")
@AllArgsConstructor
public class CaptchaVo {

    @ApiModelProperty("验证码的key")
    private String verifyKey;

    @ApiModelProperty("验证码的图片数据【base64】")
    private String base64image;

}
