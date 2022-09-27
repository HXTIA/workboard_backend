package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("【编辑】组织信息")
public class OrganizationReqVo {

    @NotNull
    @ApiModelProperty(value = "组织ID", required = true)
    private Short id;

    @NotBlank
    @ApiModelProperty(value = "组织名称", required = true)
    private String name;

    @ApiModelProperty(value = "原背景地址")
    private String background;

    @ApiModelProperty(value = "组织名称")
    private MultipartFile backgroundFile;

}
