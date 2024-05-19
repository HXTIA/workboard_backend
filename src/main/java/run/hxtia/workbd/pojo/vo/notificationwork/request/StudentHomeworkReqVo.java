package run.hxtia.workbd.pojo.vo.notificationwork.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("【编辑】作业状态")
public class StudentHomeworkReqVo {
    @NotNull
    @ApiModelProperty(value = "作业 ID", required = true)
    private Long homeworkId;

    @ApiModelProperty(value = "作业状态【0：未读未完成，1：已读未完成，2：已读已完成】")
    private Short status;

    @ApiModelProperty(value = "是否置顶【0：不置顶，1：置顶】")
    private Short pin;

    // 学生 Token
    @JsonIgnore
    private String wxToken;
}
