package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("【编辑|创建】作业信息")
public class WorkReqVo {

    @ApiModelProperty("作业ID【没有ID就是新建，ID > 0 是编辑】")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "课程ID", required = true)
    private Integer courseId;

    @NotNull
    @ApiModelProperty(value = "学期ID", required = true)
    private Integer semesterId;

    @NotBlank
    @ApiModelProperty(value = "作业标题", required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "作业内容", required = true)
    private String detail;

    @NotNull
    @ApiModelProperty(value = "截止日期", required = true)
    private Long deadline;

    @ApiModelProperty("新图片数据")
    private List<MultipartFile> pictureFiles;

}

