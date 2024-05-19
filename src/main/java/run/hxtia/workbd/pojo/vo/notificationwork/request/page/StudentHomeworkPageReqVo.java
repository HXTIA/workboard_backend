package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */
@Data
@ApiModel("学生作业分页请求")
@EqualsAndHashCode(callSuper = true)
public class StudentHomeworkPageReqVo  extends PageReqVo {
    @ApiModelProperty("学生id")
    private Integer studentId;
}
