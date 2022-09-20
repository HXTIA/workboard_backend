package run.hxtia.workbd.pojo.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("【认证】解密")
public class WxAuthLoginReqVo {

    @ApiModelProperty(value = "需要解密的用户数据", required = true)
    private String encryptedData;
    @ApiModelProperty(value = "用于解密的参数", required = true)
    private String iv;
    @ApiModelProperty(value = "原始数据", required = true)
    private String rawData;
    @ApiModelProperty(value = "签名密钥【用于验签】", required = true)
    private String signature;
    @ApiModelProperty(hidden = true)
    private String token;
}
