package run.hxtia.workbd.pojo.vo.common.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("微信作业分页模块")
public class WxWorkPageReqVo extends KeywordPageReqVo {

    @NotNull
    @ApiModelProperty("用户ID")
    private Long userId;

}
