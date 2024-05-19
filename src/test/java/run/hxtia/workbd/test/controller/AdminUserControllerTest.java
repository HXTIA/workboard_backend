//package run.hxtia.workbd.test.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.validation.annotation.Validated;
//import run.hxtia.workbd.WorkBoardApplication;
//import run.hxtia.workbd.controller.admin.AdminUserController;
//import run.hxtia.workbd.pojo.vo.usermanagement.request.AdminUserRegisterReqVo;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
//@SpringBootTest(classes = WorkBoardApplication.class)
//@Rollback
//@AutoConfigureMockMvc
//public class AdminUserControllerTest {
//
//    @Autowired
//    private AdminUserController controller;
//
//    @Test
//    public void registerTest() {
//        AdminUserRegisterReqVo reqVo = new AdminUserRegisterReqVo();
//        reqVo.setEmail("");
//        reqVo.setPassword("");
//        controller.register(reqVo);
//    }
//
//
//}
