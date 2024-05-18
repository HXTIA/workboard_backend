package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */

@Data
@AllArgsConstructor
@ApiModel("【授权码】B端授权模块，批量增加授权码请求体")
public class CodeSavaBatchReqVo {
    @ApiModelProperty("课程列表")
    private List<String> courseIds;
    @ApiModelProperty("课程列表")
    private List<String> classIds;

    @ApiModelProperty("授权码")
    private String code;
    @ApiModelProperty("生成授权码的用户ID")
    private String userId;
}
