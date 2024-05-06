package run.hxtia.workbd.pojo.vo.response.organization;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程响应对象
 */
@Data
public class CourseVo {

    @ApiModelProperty("课程ID")
    private Integer id;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("课程教师id")
    private Integer teacherId;

    @ApiModelProperty("课程所属学院id")
    private Integer collegeId;

}
