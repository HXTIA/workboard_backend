package run.hxtia.workbd.pojo.vo.notificationwork.request;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生选课请求对象
 */
@Data
@ApiModel("【保存】学生课程表实体")
public class StudentCourseReqVo {

    @ApiModelProperty(value = "学生id", required = true)
    private Integer studentId;

    @ApiModelProperty(value = "课程id", required = true)
    private Integer courseId;

}
