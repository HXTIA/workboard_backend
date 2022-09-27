package run.hxtia.workbd.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("组织信息")
public class OrganizationVo {

    @ApiModelProperty("组织ID")
    private Short id;

    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("组织背景【旗帜】")
    private String background;

}

