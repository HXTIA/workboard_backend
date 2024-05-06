package run.hxtia.workbd.pojo.vo.request.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@ApiModel("【编辑】年级实体")
public class GradeEditReqVo {

    @NotBlank
    @ApiModelProperty(value = "年级名", required = true)
    private String name;

    @ApiModelProperty(value = "年级id", required = true)
    private String id;

    @ApiModelProperty(value = "所属学院", required = true)
    private String collegeId;

}
