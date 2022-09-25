package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【保存】用户信息")
public class AdminUserReqVo {

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

    @NotNull
    @ApiModelProperty(value = "操纵者的ID【当前登录用户的ID】", required = true)
    private Short operatorId;

    @ApiModelProperty("角色ID【多个角色之间使用：, 隔开】")
    private String roleIds;

}
