package run.hxtia.workbd.pojo.vo.common.response;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxAccessTokenVo extends WxCodeMsg {

    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "expires_in")
    private Integer expiresIn;

    public static WxAccessTokenVo parseFromJson(String json) {
        return JSON.parseObject(json, WxAccessTokenVo.class);
    }

}
