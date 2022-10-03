package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("树状结构的资源")
public class ResourceDto {

    @ApiModelProperty("资源ID")
    private Short id;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("映射路由")
    private String uri;

    @ApiModelProperty("具体权限")
    private String permission;

    @ApiModelProperty("资源类型【1目录，2：菜单，3：资源】")
    private Short type;

    @ApiModelProperty("序号【可用于排序】")
    private Short sn;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("父级资源【0：无父资源】")
    private Short parentId;

    @ApiModelProperty("子级资源")
    List<ResourceDto> children;

}

