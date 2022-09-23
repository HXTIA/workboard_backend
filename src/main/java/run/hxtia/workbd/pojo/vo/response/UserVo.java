package run.hxtia.workbd.pojo.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("C端用户信息")
public class UserVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("头像地址")
    private String avatarUrl;

    @ApiModelProperty("用户姓名")
    private String nickname;

    @ApiModelProperty("学号")
    private String studentId;

    @ApiModelProperty("组织ID")
    private String orgId;

}
