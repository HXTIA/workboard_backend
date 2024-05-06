package run.hxtia.workbd.pojo.vo.response;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxTokenVo  extends WxCodeMsg  {

    private String sessionKey;
    private String unionid;
    private String openid;

    public static WxTokenVo parseFromJson(String json) {
        return JSON.parseObject(json, WxTokenVo.class);
    }

}
