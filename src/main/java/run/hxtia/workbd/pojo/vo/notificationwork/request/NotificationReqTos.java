package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */

@Data
@AllArgsConstructor
@ApiModel(" 通知发送给的对象 ")
public class NotificationReqTos {

     // 班级
    @ApiModelProperty("以班为单位，班级id（可以多选；用 ， 分隔）")
     private String classId;

    // 学生
    @ApiModelProperty("学生id（可以多选）用 ，分隔")
    private String studentId;


}
