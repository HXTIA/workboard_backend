package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseIdWorkPageReqVo extends PageReqVo {

    // 课程ids列表
    @ApiModelProperty("课程ID列表")
    private List<Integer> courseIds;
}
