package run.hxtia.workbd.pojo.vo.usermanagement.request.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentWorkPageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("学生ID")
    private String wechatId;

    @ApiModelProperty("截止时间。传入一个数组【一个时间范围：开始 - 结束】")
    private Date[] deadline;

    @ApiModelProperty("创建时间，传入一个数组【一个时间范围：开始 - 结束】")
    private Date[] createdTime;

    @ApiModelProperty("课程ID")
    private Integer courseId;

    @ApiModelProperty("发布者ID")
    private Integer publisherId;

    @ApiModelProperty("筛选作业状态【不传值：全量数据 0：未读未完成，1：已读未完成，2：已读已完成】")
    private Short status;

    @ApiModelProperty("筛选置顶【0：不置顶，1：置顶】")
    private Short pin;

    // 学生 WXToken
    @JsonIgnore
    private String wxToken;
}
