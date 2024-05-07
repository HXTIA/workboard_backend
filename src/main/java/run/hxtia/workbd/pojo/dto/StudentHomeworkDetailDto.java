package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("学生作业详情信息")
public class StudentHomeworkDetailDto {

    @ApiModelProperty("作业ID")
    private Long id;

    @ApiModelProperty("作业标题")
    private String title;

    @ApiModelProperty("作业描述")
    private String description;

    @ApiModelProperty("图片链接")
    private String pictureLinks;

    @ApiModelProperty("截止日期")
    private Long deadline;

    @ApiModelProperty("作业状态【0：未读未完成，1：已读未完成，2：已读已完成】")
    private Short status;

    @ApiModelProperty("是否置顶【0：不置顶，1：置顶】")
    private Short pin;
}
