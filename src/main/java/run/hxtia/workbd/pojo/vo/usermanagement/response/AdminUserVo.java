package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("后台管理用户信息")
public class AdminUserVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像url")
    private String avatarUrl;

    @ApiModelProperty("学院ID")
    private Integer collegeId;

    @ApiModelProperty("姓名")
    private String nickname;

    @ApiModelProperty("用户状态")
    private Short status;

}
