package run.hxtia.workbd.pojo.vo.notificationwork.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("作业信息")
public class HomeworkVo {

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

    @ApiModelProperty("创建时间")
    private Long createdAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    @ApiModelProperty("课程ID")
    private Integer courseId;

    @ApiModelProperty("发布者ID")
    private Integer publisherId;

    @ApiModelProperty("发布平台")
    private String publishPlatform;

    @ApiModelProperty("状态")
    private Short status;


}

