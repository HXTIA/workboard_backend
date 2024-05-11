package run.hxtia.workbd.pojo.vo.common.response;


import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxAccessTokenVo extends WxCodeMsg {

    private String accessToken;
    private Integer expiresIn;

    public static WxAccessTokenVo parseFromJson(String json) {
        return JSON.parseObject(json, WxAccessTokenVo.class);
    }

}
