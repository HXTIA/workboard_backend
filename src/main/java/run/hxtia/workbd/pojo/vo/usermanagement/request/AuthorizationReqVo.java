package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */

@Data
@AllArgsConstructor
@ApiModel("【保存】授权信息")
public class AuthorizationReqVo {

    @ApiModelProperty("权限")
    private String permissions;

    @ApiModelProperty("发布者id")
    private Long publisherId;
}
