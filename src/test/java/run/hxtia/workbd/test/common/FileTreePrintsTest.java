package run.hxtia.workbd.test.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import run.hxtia.workbd.common.util.FileTreePrints;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@ContextConfiguration
@SpringBootConfiguration
public class FileTreePrintsTest {

    @Test
    public void showFileDif() throws Exception {
        String path = "D:\\JaVa\\IdeaProjects\\work-board";
        String fileDir = FileTreePrints.generate(
            path,
            new HashSet<>(Arrays.asList(".idea", "target", ".git", "test")));
        System.out.print(fileDir);
    }
}
