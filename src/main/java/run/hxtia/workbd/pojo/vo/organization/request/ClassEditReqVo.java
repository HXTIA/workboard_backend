package run.hxtia.workbd.pojo.vo.organization.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@ApiModel("【编辑】班级实体")
public class ClassEditReqVo {

    @NotBlank
    @ApiModelProperty(value = "班级名", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "班级id", required = true)
    private String id;

    @NotBlank
    @ApiModelProperty(value = "所属年级", required = true)
    private String gradeId;
}
