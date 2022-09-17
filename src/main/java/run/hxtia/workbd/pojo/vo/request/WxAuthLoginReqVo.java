package run.hxtia.workbd.pojo.vo.request;

import lombok.Data;

@Data
public class WxAuthLoginReqVo {
    private String encryptedData;
    private String iv;
    private String rawData;
    private String signature;
    private String token;
}
