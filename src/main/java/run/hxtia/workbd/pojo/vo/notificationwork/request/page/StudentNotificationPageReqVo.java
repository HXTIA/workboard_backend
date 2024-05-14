package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */

@Data
@ApiModel("学生通知分页请求")
@EqualsAndHashCode(callSuper = true)
public class StudentNotificationPageReqVo extends PageReqVo {

        @ApiModelProperty("微信id")
        private String wechatId;
}
