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
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.RoleReqVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageJsonVo;
import run.hxtia.workbd.service.admin.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/admin/roles")
@Api(tags = "RoleController")
@Tag(name = "RoleController", description = "角色模块")
@RequiredArgsConstructor
public class RoleController extends BaseController<Role, RoleReqVo> {

    private final RoleService roleService;

    @PostMapping("/searchListPage")
    @ApiOperation("角色列表【关键字、分页】")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_READ)
    public PageJsonVo<RoleVo> searchListPage(@RequestBody RolePageReqVo pageReqVo) {
        return JsonVos.ok(roleService.list(pageReqVo));
    }

    @GetMapping("/searchList")
    @ApiOperation("获取所有角色")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_READ)
    public DataJsonVo<List<RoleVo>> searchList() {
        return JsonVos.ok(Streams.map(roleService.list(), MapStructs.INSTANCE::po2vo));
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_CREATE)
    public JsonVo create(@Valid @RequestBody RoleReqVo reqVo) {
        if (roleService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_UPDATE)
    public JsonVo update(@Valid @RequestBody RoleReqVo reqVo) {
        return create(reqVo);
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Role> getService() {
        return roleService;
    }

    @Override
    protected Function<RoleReqVo, Role> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
