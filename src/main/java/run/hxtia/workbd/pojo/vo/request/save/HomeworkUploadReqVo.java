package run.hxtia.workbd.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("【编辑】作业图片编辑")
public class HomeworkUploadReqVo {

    @NotNull
    @ApiModelProperty(value = "作业ID", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "作业图片原URL", required = true)
    private String pictures;

    @ApiModelProperty("图片数据")
    private List<MultipartFile> picturesFiles;

    @NotEmpty
    @ApiModelProperty(value = "对标数组【以前的图片数组的位置为索引，" +
        "值为是否修改，若修改为1，未修改为0】", required = true)
    private List<Integer> matchIndex;

}
