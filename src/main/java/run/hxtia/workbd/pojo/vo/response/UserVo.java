package run.hxtia.workbd.pojo.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("用户信息")
public class UserVo {


    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("注册时间")
    private Long createdTime;

    @ApiModelProperty("上次登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "用户邮箱")
    private String email;


    @ApiModelProperty("用户用户昵称")
    private String nickname;


    @ApiModelProperty("用户状态【1：可用；0：禁用】")
    private Short state;


}
