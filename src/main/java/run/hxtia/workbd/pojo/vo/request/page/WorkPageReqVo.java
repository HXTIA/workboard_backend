package run.hxtia.workbd.pojo.vo.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.request.page.base.KeywordPageReqVo;

import java.util.Date;

@Data
@ApiModel("作业模块分页请求")
@EqualsAndHashCode(callSuper = true)
public class WorkPageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("截止时间。传入一个数组【一个时间范围：开始 - 结束】")
    private Date[] deadline;

    @ApiModelProperty("创建时间，传入一个数组【一个时间范围：开始 - 结束】")
    private Date[] createdTime;

    @ApiModelProperty("学期ID")
    private Integer semesterId;

    @ApiModelProperty("课程ID")
    private Integer courseId;

}
