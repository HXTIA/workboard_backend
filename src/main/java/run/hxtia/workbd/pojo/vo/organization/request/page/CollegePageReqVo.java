package run.hxtia.workbd.pojo.vo.organization.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;

@Data
@ApiModel("用户信息分页请求Vo")
@EqualsAndHashCode(callSuper = true)
public class CollegePageReqVo extends KeywordPageReqVo {
//    @ApiModelProperty("学院ID")
//    private Integer id;
}
