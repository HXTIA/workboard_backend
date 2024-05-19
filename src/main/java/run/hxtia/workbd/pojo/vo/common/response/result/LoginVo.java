package run.hxtia.workbd.pojo.vo.common.response.result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("登录成功的结果")
public class LoginVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("登录令牌")
    private String token;


}
