package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AdminUserVo;

import java.util.List;

@Data
@ApiModel("用户信息 + 角色 + 组织")
public class AdminUserInfoDto {

    @ApiModelProperty("用户信息")
    private AdminUserVo userVo;

    @ApiModelProperty("角色信息")
    private List<Role> roles;

    // @ApiModelProperty("组织信息")
    //private OrganizationVo orgVo;

}
