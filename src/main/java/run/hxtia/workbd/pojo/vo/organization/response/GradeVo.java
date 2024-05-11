package run.hxtia.workbd.pojo.vo.organization.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@ApiModel("年级信息")
public class GradeVo {

    @ApiModelProperty("年级ID")
    private Integer id;

    @ApiModelProperty("年级名称")
    private String name;

    @ApiModelProperty("所属学院id")
    private String collegeId;
}
