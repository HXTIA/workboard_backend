package run.hxtia.workbd.pojo.vo.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.request.page.base.KeywordPageReqVo;
import run.hxtia.workbd.pojo.vo.request.page.base.PageReqVo;

import java.util.Date;


@Data
@ApiModel("组织分页请求Vo")
@EqualsAndHashCode(callSuper = true)
public class OrganizationPageReqVo extends KeywordPageReqVo {
    // 若没有特殊需求，留个空空壳子就行

    @ApiModelProperty("创建时间，传入一个数组【一个时间范围：开始 - 结束】")
    private Date[] createdTime;
}
