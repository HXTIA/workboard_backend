package run.hxtia.workbd.pojo.vo.request.organization;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
public class CourseEditReqVo {

    @NotBlank
    @ApiModelProperty(value = "课程名", required = true)
    private String name;

    @ApiModelProperty(value = "课程id", required = true)
    private String id;

    @ApiModelProperty(value = "课程描述", required = true)
    private String description;

    @ApiModelProperty(value = "课程教师id", required = true)
    private Integer teacherId;

    @ApiModelProperty(value = "课程所属学院id", required = true)
    private Integer collegeId;

}
