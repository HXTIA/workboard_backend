package run.hxtia.workbd.pojo.vo.organization.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@ApiModel("班级信息")
public class ClassVo {

    @ApiModelProperty("班级ID")
    private Integer id;

    @ApiModelProperty("班级名称")
    private String name;

    @ApiModelProperty("所属年级id")
    private Integer gradeId;
}
