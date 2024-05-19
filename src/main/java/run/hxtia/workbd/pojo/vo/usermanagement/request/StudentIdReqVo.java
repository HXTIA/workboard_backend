package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */
@Data
@ApiModel("学生id请求对象")
public class StudentIdReqVo {
    @ApiModelProperty("学生ID")
    @NotNull(message = "学生ID不能为空")
    private String studentId;
}
