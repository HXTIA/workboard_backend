package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【创建|编辑】角色实体")
public class RoleReqVo {

    @ApiModelProperty("角色ID【传ID就是编辑，否则是创建】")
    private Short id;

    @NotBlank
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty("角色简介")
    private String intro;

}

