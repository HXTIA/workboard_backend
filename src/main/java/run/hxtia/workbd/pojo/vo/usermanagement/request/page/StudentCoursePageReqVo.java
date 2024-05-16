package run.hxtia.workbd.pojo.vo.usermanagement.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

import javax.validation.constraints.NotNull;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentCoursePageReqVo extends KeywordPageReqVo {

    @NotNull(message = "学生ID不能为空")
    @ApiModelProperty("学生ID")
    private String wechatId;
}
