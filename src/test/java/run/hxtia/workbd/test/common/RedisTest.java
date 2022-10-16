package run.hxtia.workbd.test.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.pojo.po.Role;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class RedisTest {

    @Autowired
    private Redises redises;

    @Data
    @AllArgsConstructor
    private static class TestPo {
        private Integer id;
        private String name;
    }

    @Test
    public void testList() {
        String key = "roles:list";
        List<TestPo> roles = new ArrayList<>();
        roles.add(new TestPo(1, "哈哈"));
        roles.add(new TestPo(2, "啊啊啊"));
        roles.add(new TestPo(3, "啊啊撒"));
        redises.set(key, roles);

        List<TestPo> o = (List<TestPo>) redises.get(key);

        System.out.println(o);

    }

    @Test
    public void test() {
        String key = "roles:obj";

        redises.set(key, new TestPo(3, "啊啊撒"));

        TestPo o = (TestPo) redises.get(key);

        System.out.println(o);

    }

}
