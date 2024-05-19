package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */

@Data
@ApiModel("授权码信息")
public class CodesVo {

        @ApiModelProperty("授权码ID")
        private Integer id;

        @ApiModelProperty("授权码")
        private String code;

        @ApiModelProperty("生成授权码的用户ID")
        private Integer userId;

        @ApiModelProperty("课程ID")
        private Integer courseId;

        @ApiModelProperty("班级ID")
        private Integer classId;

        @ApiModelProperty("授权码状态（1表示未使用，2表示已使用，3表示已吊销）")
        private Integer status;

        @ApiModelProperty("记录创建时间")
        private String createdAt;
}
