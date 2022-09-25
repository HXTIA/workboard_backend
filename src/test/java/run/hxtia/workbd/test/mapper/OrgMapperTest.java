package run.hxtia.workbd.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.mapper.OrganizationMapper;
import run.hxtia.workbd.pojo.po.Organization;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class OrgMapperTest {

    @Autowired
    OrganizationMapper organizationMapper;

    @Test
    public void testKey() {

        Organization organization = new Organization();
        int i = organizationMapper.insertDefaultRegister(organization);
        System.out.println(organization.getId());

    }
}
