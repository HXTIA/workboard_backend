package run.hxtia.workbd.pojo.vo.usermanagement.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;


@Data
@ApiModel("用户个人主页信息")
@EqualsAndHashCode(callSuper = true)
public class AdminUserInfoEditReqVo extends AdminUserEdit {

    @ApiModelProperty("原先url地址")
    private String avatarUrl;

    @ApiModelProperty("头像图片数据")
    private MultipartFile avatarFile;

}
