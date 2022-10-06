package run.hxtia.workbd.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.AdminUserInfoDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.*;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.admin.AdminUserService;
import run.hxtia.workbd.service.admin.EmailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.function.Function;


@RestController
@RequestMapping("/admin/users")
@Api(tags = "AdminUserController")
@Tag(name = "AdminUserController", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController extends BaseController<AdminUsers, AdminUserReqVo> {

    private final AdminUserService adminUserService;
    private final EmailService emailService;

    @GetMapping("/sendEmail")
    @ApiOperation("发送邮箱验证码")
    public JsonVo sendEmail(@Email(message = "请输入正确的邮件地址")
                                @RequestParam String email) throws Exception {
        emailService.sendHTMLEmail(email, "验证码");
        return JsonVos.ok("发送成功，请查收邮件");
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public DataJsonVo<AdminLoginVo> login(@Valid @RequestBody AdminLoginReqVo reqVo) {
        return JsonVos.ok(adminUserService.login(reqVo));
    }

    @PostMapping("/register")
    @ApiOperation("注册超管【组织发起者】")
    public JsonVo register(@Valid @RequestBody AdminUserRegisterReqVo reqVo) {
        if (adminUserService.register(reqVo)) {
            return JsonVos.ok(CodeMsg.REGISTER_OK);
        } else {
            return JsonVos.error(CodeMsg.REGISTER_ERROR);
        }
    }

    @PostMapping("/forgotPwd")
    @ApiOperation("找回密码【仅组织发起者可用】")
    public JsonVo forgotPwd(@Valid @RequestBody AdminUserForgotReqVo reqVo) {
        if (adminUserService.forgotPwd(reqVo)) {
            return JsonVos.ok(CodeMsg.UPDATE_PWD_OK);
        } else {
            return JsonVos.error(CodeMsg.UPDATE_PWD_ERROR);
        }
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@NotNull(message = "ID不能为空") Long id) {
        // 清空缓存中的token就可以了
        Redises.getRedises().delByUserId(id);
        return JsonVos.ok();
    }

    @Override
    @ApiOperation("添加用户【必须有操纵者ID】")
    public JsonVo create(@Valid @RequestBody AdminUserReqVo reqVo) {
        if (adminUserService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("编辑用户【必须有待编辑者ID】")
    public JsonVo edit(@Valid @RequestBody AdminUserEditReqVo reqVo) {
        if (adminUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/updateInfo")
    @ApiOperation("修改用户个人信息【必须有待编辑者ID】")
    public JsonVo update(@Valid AdminUserInfoEditReqVo reqVo) throws Exception {
        if (adminUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/updatePassword")
    @ApiOperation("修改密码【仅自己修改】")
    public JsonVo updatePassword(@Valid @RequestBody AdminUserPasswordReqVo reqVo) {
        if (adminUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // TODO: 合并后添加上sysAdminUser:forgot权限
    @PostMapping("/updateMemberPwd")
    @ApiOperation("修改组织成员密码【仅自己修改】")
    public JsonVo updateMemberPwd(@Valid @RequestBody AdminUserMemberPwdReqVo reqVo) {
        if (adminUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.UPDATE_PWD_OK);
        } else {
            return JsonVos.error(CodeMsg.UPDATE_PWD_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("通过Id获取用户信息【角色 + 组织 + 个人信息】")
    public DataJsonVo<AdminUserInfoDto> searchAdminUserInfo(@PathVariable @NotNull Integer id) {
        return JsonVos.ok(adminUserService.getAdminUserInfo(id));
    }

    @Override
    @ApiOperation("这是一个无用接口")
    public JsonVo update(AdminUserReqVo reqVo) {
        return JsonVos.error("更新用户信息请访问接口【/updateInfo】");
    }

    @Override
    protected IService<AdminUsers> getService() {
        return adminUserService;
    }

    @Override
    protected Function<AdminUserReqVo, AdminUsers> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
