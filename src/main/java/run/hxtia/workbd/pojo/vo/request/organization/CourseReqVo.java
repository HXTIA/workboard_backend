package run.hxtia.workbd.pojo.vo.request.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Data
@ApiModel("【保存】课程实体")
public class CourseReqVo {

    @NotBlank
    @ApiModelProperty(value = "课程名", required = true)
    private String name;

    @ApiModelProperty(value = "课程描述", required = true)
    private String description;

    @ApiModelProperty(value = "课程教师id", required = true)
    private Integer teacherId;

    @ApiModelProperty(value = "课程所属学院id", required = true)
    private Integer collegeId;

}
