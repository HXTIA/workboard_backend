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
@ApiModel("【保存】班级实体")
public class ClassReqVo {

    @NotBlank
    @ApiModelProperty(value = "班级名", required = true)
    private String name;

    @ApiModelProperty(value = "所属年级", required = true)
    private String gradeId;
}
