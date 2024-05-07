package run.hxtia.workbd.pojo.vo.request.course;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 学生选课请求对象
 */
@Data
@ApiModel("【编辑】学生课程表实体")
public class StudentCourseEditReqVo {

    @NotNull
    @ApiModelProperty(value = "学生课程id", required = true)
    private Integer id;

    @ApiModelProperty(value = "学生id", required = true)
    private Integer studentId;

    @ApiModelProperty(value = "课程id", required = true)
    private Integer courseId;

}
