package run.hxtia.workbd.pojo.vo.usermanagement.request.page;

import io.swagger.annotations.ApiModelProperty;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

import javax.validation.constraints.NotNull;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
public class StudentCoursePageReqVo extends PageReqVo {

    @NotNull(message = "学生ID不能为空")
    @ApiModelProperty("学生ID")
    private Integer studentId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
