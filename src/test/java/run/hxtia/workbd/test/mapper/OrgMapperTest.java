package run.hxtia.workbd.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class OrgMapperTest {

    @Autowired
    CollegeMapper collegeMapper;

    @Test
    public void testKey() {

        Organization organization = new Organization();
        int i = collegeMapper.insertDefaultRegisterOrg(organization);
        System.out.println(organization.getId());

    }
}
