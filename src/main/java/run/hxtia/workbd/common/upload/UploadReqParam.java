package run.hxtia.workbd.common.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("单文件请求参数")
public class UploadReqParam {

    /**
     * 原始路径
     */
    private String originUri;
    /**
     * 新传的文件
     */
    private MultipartFile file;
}
