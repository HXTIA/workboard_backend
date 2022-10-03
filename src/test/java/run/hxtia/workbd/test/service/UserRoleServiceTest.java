package run.hxtia.workbd.test.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.pojo.po.AdminUserRole;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.service.admin.AdminUserRoleService;
import run.hxtia.workbd.service.admin.RoleService;

import java.util.List;


@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class UserRoleServiceTest {

    @Autowired
    private AdminUserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Test
    public void roleIdsTest() {
        List<Short> shorts = userRoleService.listRoleIds(5);
        List<Role> roles = roleService.listByIds(shorts);
        System.out.println(roles);
    }

}
