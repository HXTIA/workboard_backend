package run.hxtia.workbd.controller.admin.usermanager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.RoleReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.RoleVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.JsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.service.usermanagement.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
@RestController
@RequestMapping("/admin/userManager/roles")
@Api(tags = "RoleController")
@Tag(name = "RoleController", description = "【B端】角色模块")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/searchListPage")
    @ApiOperation("角色列表【关键字、分页】")
    @RequiresPermissions(Constants.Permission.ROLE_READ)
    public PageJsonVo<RoleVo> searchListPage(@RequestBody RolePageReqVo pageReqVo, HttpServletRequest request) {
        String token = request.getHeader(Constants.Web.HEADER_TOKEN);
        return JsonVos.ok(roleService.list(pageReqVo, token));
    }

    @GetMapping("/searchList")
    @ApiOperation("获取所有角色")
    @RequiresPermissions(Constants.Permission.ROLE_READ)
    public DataJsonVo<List<RoleVo>> searchList(HttpServletRequest request) {
        String token = request.getHeader(Constants.Web.HEADER_TOKEN);
        return JsonVos.ok(Streams.list2List(roleService.list(token), MapStructs.INSTANCE::po2vo));
    }

    @PostMapping("/create")
    @ApiOperation("添加")
    @RequiresPermissions(Constants.Permission.ROLE_CREATE)
    public JsonVo create(@Valid @RequestBody RoleReqVo reqVo, HttpServletRequest request) {
        String token = request.getHeader(Constants.Web.HEADER_TOKEN);
        if (roleService.saveOrUpdate(reqVo, token)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    @RequiresPermissions(Constants.Permission.ROLE_UPDATE)
    public JsonVo update(@Valid @RequestBody RoleReqVo reqVo, HttpServletRequest request) {
        return create(reqVo, request);
    }

    @PostMapping("/remove")
    @ApiOperation("删除一条或者多条数据【多个ID间用逗号(,)隔开】")
    @RequiresPermissions(Constants.Permission.ROLE_DELETE)
    public JsonVo remove(String ids) {
        if (roleService.removeByIds(Arrays.asList(ids.split(",")))) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

}
