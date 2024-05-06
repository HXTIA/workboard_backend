package run.hxtia.workbd.pojo.vo.response;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

@Data
public class WxCodeMsg {
    private Integer errcode;
    private String errmsg;

    public static WxCodeMsg parseFromJson(String json) {
        return JSON.parseObject(json, WxCodeMsg.class);
    }
}
