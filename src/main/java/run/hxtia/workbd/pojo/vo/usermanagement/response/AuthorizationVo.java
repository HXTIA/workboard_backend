package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */


@Data
@ApiModel("授权信息")
public class AuthorizationVo {

    @ApiModelProperty("授权id")
    private Integer Id;

    @ApiModelProperty("授权内容")
    private String permissions;

    @ApiModelProperty("发布者id")
    private Long publisherId;
}
