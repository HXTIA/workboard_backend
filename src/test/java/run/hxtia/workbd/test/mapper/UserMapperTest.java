package run.hxtia.workbd.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.mapper.UserMapper;
import run.hxtia.workbd.pojo.po.User;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testKey() {

        User user = new User();
        user.setNickname("222");
        user.setOpenid("221");

        userMapper.insert(user);

        System.out.println(user.getId());
        System.out.println(user.getNickname());

    }
}
