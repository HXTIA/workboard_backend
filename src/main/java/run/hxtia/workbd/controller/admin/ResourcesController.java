package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.ResourceDto;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.service.admin.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/admin/resources")
@Api(tags = "ResourcesController")
@Tag(name = "ResourcesController", description = "资源模块")
@RequiredArgsConstructor
public class ResourcesController {

    private final ResourceService resourceService;

    @GetMapping("/searchMenu")
    @ApiOperation("根据角色Id查询角色菜单")
    public DataJsonVo<List<ResourceDto>> searchMenu(
        @ApiParam("角色ID，多个角色之间使用逗号隔开") String roleIds) {
        return JsonVos.ok(resourceService.listMenu(roleIds), "资源获取成功");
    }

    @GetMapping("/searchList")
    @ApiOperation("返回完整的树状结构的资源")
    public DataJsonVo<List<ResourceDto>> searchList() {
        return JsonVos.ok(resourceService.listAllTree(), "资源获取成功");
    }

}
