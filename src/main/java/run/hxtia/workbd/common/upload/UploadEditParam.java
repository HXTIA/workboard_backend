package run.hxtia.workbd.common.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadEditParam {
    /**
     * 新传的文件数据
     */
    private MultipartFile[] newFiles;
    /**
     * 以前文件的对标索引
     */
    private List<Integer> matchIndex;
    /**
     * 以前文件存数据库的路径
     */
    private String oldFilesPath;
}
