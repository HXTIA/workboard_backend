package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */


@Data
@AllArgsConstructor
@ApiModel("【编辑|保存】学生授权信息")
public class StudentAuthorizationReqVo {

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("课程id(列表)")
    private String courseId;

    @ApiModelProperty("班级id(列表)")
    private String classId;

}
