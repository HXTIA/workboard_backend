package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【编辑|保存】用户信息")
public class AdminUserReqVo {

    @ApiModelProperty("用户id【大于0是编辑，否则是保存】")
    private Long id;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

    @ApiModelProperty("头像url")
    private String avatarUrl;

    @ApiModelProperty("组织ID【在超管第一次创建管理员的时候，才需要传组织ID】")
    private Integer orgId;

}
