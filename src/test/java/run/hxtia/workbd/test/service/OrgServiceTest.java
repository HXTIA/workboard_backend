package run.hxtia.workbd.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.service.admin.OrganizationService;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class OrgServiceTest {

    @Autowired
    private OrganizationService orgService;

    @Test
    public void test() {
        Organization organization = new Organization();
        organization.setBackground("");
        orgService.save(organization);
    }

}
