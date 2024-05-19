package run.hxtia.workbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 程序入口
 */
@SpringBootApplication
public class WorkBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkBoardApplication.class, args);
    }
}
