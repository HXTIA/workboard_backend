package run.hxtia.workbd.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.pojo.po.Homework;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class WorkServiceTest {

    @Autowired
    private WorkService workService;

    @Test
    public void testListByIds() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("4");
        list.add("2");
        List<Homework> homework = workService.listByIds(list);
        System.out.println(homework);
    }

}
