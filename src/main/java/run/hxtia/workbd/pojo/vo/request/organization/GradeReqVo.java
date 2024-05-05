package run.hxtia.workbd.pojo.vo.request.organization;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 年级请求实体
 */
@Data
@ApiModel("【保存】年级实体")
public class GradeReqVo {

    @NotBlank
    @ApiModelProperty(value = "年级名称", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "所属学院ID", required = true)
    private Integer collegeId;
}
