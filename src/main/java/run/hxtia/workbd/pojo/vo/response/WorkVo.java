package run.hxtia.workbd.pojo.vo.response;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("作业信息")
public class WorkVo {

    @ApiModelProperty("作业ID")
    private Long id;

    @ApiModelProperty("作业标题")
    private String title;

    @ApiModelProperty("作业内容")
    private String detail;

    @ApiModelProperty("截止日期")
    private Long deadline;

    @ApiModelProperty("作业图片")
    private String pictures;

    @ApiModelProperty("创建时间")
    private Long createdAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

}

