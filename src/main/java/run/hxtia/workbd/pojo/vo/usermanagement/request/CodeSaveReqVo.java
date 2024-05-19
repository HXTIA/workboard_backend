package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */

@Data
@AllArgsConstructor
@ApiModel("【授权码】授权模块，保存授权码信息")
public class CodeSaveReqVo {

    @ApiModelProperty("授权码")
    private String code;

    @ApiModelProperty("发布者id")
    private Integer publishId;

    @ApiModelProperty("课程id")
    private String courseId;

    @ApiModelProperty("班级id")
    private String classId;

    @ApiModelProperty("状态")
    private Integer status;

}
