package run.hxtia.workbd.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.function.Function;


@RestController
@RequestMapping("/admin/users")
@Api(tags = "AdminUserController")
@Tag(name = "AdminUserController", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController extends BaseController<AdminUsers, AdminUserReqVo> {

    private final AdminUserService adminUserService;

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

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@NotNull(message = "ID不能为空") Long id) {
        // 清空缓存中的token就可以了
        Redises.getRedises().delByUserId(id);
        return JsonVos.ok();
    }

    @Override
    @ApiOperation("添加用户【必须有操纵者ID】")
    @RequiresPermissions(Constants.Permission.SYS_ADMIN_USER_CREATE)
    public JsonVo create(@Valid @RequestBody AdminUserReqVo reqVo) {
        if (adminUserService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("编辑用户【必须有待编辑者ID】")
    @RequiresPermissions(Constants.Permission.SYS_ADMIN_USER_UPDATE)
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
            // 修改成功将其踢下线【重新登录】
            Redises.getRedises().delByUserId(reqVo.getId());
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("通过Id获取用户信息【角色 + 组织 + 个人信息】")
    public DataJsonVo<AdminUserInfoDto> searchAdminUserInfo(@PathVariable @NotNull Long id) {
        return JsonVos.ok(adminUserService.getAdminUserInfo(id));
    }

    @Override
    @ApiOperation("这是一个无用接口")
    public JsonVo update(AdminUserReqVo reqVo) {
        return JsonVos.error("更新用户信息请访问接口【/updateInfo】");
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ADMIN_USER_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
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
