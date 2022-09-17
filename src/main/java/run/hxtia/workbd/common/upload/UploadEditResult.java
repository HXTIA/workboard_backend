package run.hxtia.workbd.common.upload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadEditResult {

    // 数据库最终存储的路径
    private String filePath;

    // 需要删除的图片
    private String needDeletePath;

    // 若有新图片上传成功后的路径
    private String newUploadFilePath;

}
