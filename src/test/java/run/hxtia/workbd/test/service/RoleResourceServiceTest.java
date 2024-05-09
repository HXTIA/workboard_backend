package run.hxtia.workbd.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.pojo.po.RoleResource;
import run.hxtia.workbd.service.UserManagement.admin.RoleResourceService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class RoleResourceServiceTest {

    @Autowired
    private RoleResourceService roleResourceService;

    @Test
    public void addRecordTest() {

        List<RoleResource> roleResources = new ArrayList<>();

        short roleId = 1;

        for (short i = 1; i <= 38; i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(i);
            roleResources.add(roleResource);
        }
        roleResourceService.saveBatch(roleResources);

    }

}
