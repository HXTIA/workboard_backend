package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CoursePageReqVo extends PageReqVo {

    @ApiModelProperty("学院ID")
    private Integer collegeId;
}
