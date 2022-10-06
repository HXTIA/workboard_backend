package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【编辑】修改组织成员密码")
public class AdminUserMemberPwdReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
    
    // TODO：添加图片验证码

}
