package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("【编辑|保存】用户信息")
public class AdminUserEditReqVo {

    @NotNull
    @ApiModelProperty("用户id【大于0是编辑，否则是保存】")
    private Long id;

    @ApiModelProperty("用户用户昵称")
    private String nickname;

    @ApiModelProperty("原先url地址")
    private String avatarUrl;

    @ApiModelProperty("头像图片数据")
    private MultipartFile avatarFile;

    @ApiModelProperty("角色ID【多个角色之间使用：, 隔开】")
    private String roleIds;

}
