package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("【编辑】用户头像上传")
public class UserAvatarReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @ApiModelProperty("原先url地址")
    private String avatarUrl;

    @ApiModelProperty("头像图片数据")
    private MultipartFile avatarFile;

}

