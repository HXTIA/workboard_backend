package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Data
@ApiModel("【保存】学生课程表并且保存作业实体")
public class SaveCoursesAndHomeworksReqVo {

    @NotNull(message = "课程ID列表不能为空")
    private List<Integer> courseIds;

    @NotNull(message = "学生ID不能为空")
    private String studentId;

}
