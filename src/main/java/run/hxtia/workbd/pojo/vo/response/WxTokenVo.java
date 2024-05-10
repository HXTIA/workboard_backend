package run.hxtia.workbd.pojo.vo.response;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxTokenVo  extends WxCodeMsg  {

    @JSONField(name = "session_key")
    private String sessionKey;
    private String unionid;
    private String openid;

    public static WxTokenVo parseFromJson(String json) {
        return JSON.parseObject(json, WxTokenVo.class);
    }

}
