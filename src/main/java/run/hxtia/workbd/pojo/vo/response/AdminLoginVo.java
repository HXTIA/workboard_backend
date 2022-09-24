package run.hxtia.workbd.pojo.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("登录后的信息【Token + 用户基本信息】")
public class AdminLoginVo {

    @ApiModelProperty("用户id【大于0是编辑，否则是保存】")
    private Long id;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

    @ApiModelProperty("头像url")
    private String avatarUrl;

    @ApiModelProperty("组织ID")
    private Integer orgId;

    @ApiModelProperty("登录令牌")
    private String token;

}
