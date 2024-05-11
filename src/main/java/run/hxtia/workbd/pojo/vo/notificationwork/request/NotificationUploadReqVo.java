package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("【编辑】通知图片编辑")
public class NotificationUploadReqVo {

    @NotNull
    @ApiModelProperty(value = "通知ID", required = true)
    private String notification_id;

    @NotNull
    @ApiModelProperty(value = "通知图片原URL", required = true)
    private String pictures;

    @ApiModelProperty("图片数据")
    private List<MultipartFile> picturesFiles;

    @NotEmpty
    @ApiModelProperty(value = "对标数组【以前的图片数组的位置为索引，" +
        "值为是否修改，若修改为1，未修改为0】", required = true)
    private List<Integer> matchIndex;
}
