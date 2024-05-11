package run.hxtia.workbd.pojo.vo.usermanagement.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@ApiModel("权限栏里的信息修改")
@EqualsAndHashCode(callSuper = true)
public class AdminUserEditReqVo extends AdminUserEdit {

    @ApiModelProperty("角色ID【多个角色之间使用：, 隔开】")
    private String roleIds;

}
