package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import run.hxtia.workbd.common.upload.UploadReqParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("【编辑】完善用户信息")
public class UserReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @ApiModelProperty("用户姓名")
    private String nickname;

    @NotBlank
    @ApiModelProperty(value = "学号", required = true)
    private String studentId;

    @NotNull
    @ApiModelProperty(value = "组织ID", required = true)
    private Short orgId;

}

