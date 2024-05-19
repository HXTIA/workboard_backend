package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("【编辑|创建】作业信息")
public class HomeworkReqVo {

    @ApiModelProperty("作业ID【没有ID就是新建，ID > 0 是编辑】")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "课程ID", required = true)
    private Integer courseId;

    @NotNull
    @ApiModelProperty(value = "发布者 ID", required = true)
    private String publisherId;

    @NotBlank
    @ApiModelProperty(value = "作业标题", required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "作业描述", required = true)
    private String description;

    @NotNull
    @ApiModelProperty(value = "截止日期", required = true)
    private Long deadline;

    @ApiModelProperty("新图片数据")
    private List<MultipartFile> pictureFiles;

    // "发布平台" Wx、Web
    private String publishPlatform;

    // C 端学生想要发布作业，需要 Token
    private String wxToken;

    public void fillInfo(String publishPlatform, String wxToken) {
        this.publishPlatform = publishPlatform;
        this.wxToken = wxToken;
    }
}
