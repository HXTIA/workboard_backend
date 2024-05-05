package run.hxtia.workbd.pojo.vo.response.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@ApiModel("学院信息")
public class CollegeVo {

    @ApiModelProperty("学院ID")
    private Integer id;

    @ApiModelProperty("学院名称")
    private String name;

    @ApiModelProperty("学院logo")
    private String logoUrl;
}
