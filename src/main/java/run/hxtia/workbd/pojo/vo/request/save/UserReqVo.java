package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【添加 & 编辑】用户信息")
public class UserReqVo {

    @ApiModelProperty("用户id【大于0是编辑，否则是保存】")
    private Integer id;

    @Email
    @ApiModelProperty(value = "用户邮箱【必须包含@】", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty("用户用户昵称")
    private String nickname;



}
