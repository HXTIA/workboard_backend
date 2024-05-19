package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */
@Data
@ApiModel("学生授权信息返回对象")
public class StudentAuthorizationVo {

    @ApiModelProperty("学生ID")
    private String studentId;

    @ApiModelProperty("课程ID")
    private String courseId;

    @ApiModelProperty("班级ID")
    private String classId;
}
