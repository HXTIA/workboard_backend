package run.hxtia.workbd.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import run.hxtia.workbd.common.utils.FileTreePrint;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@ContextConfiguration
@SpringBootConfiguration
public class FileTreePrintTest {

    @Test
    public void showFileDif() throws Exception {
        String path = "D:\\JaVa\\IdeaProjects\\work-board";
        String fileDir = FileTreePrint.generate(
            path,
            new HashSet<>(Arrays.asList(".idea", "target", ".git", "test")));
        System.out.print(fileDir);
    }
}
