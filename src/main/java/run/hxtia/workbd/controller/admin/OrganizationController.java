package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.vo.request.page.OrganizationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.OrganizationReqVo;
import run.hxtia.workbd.pojo.vo.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageJsonVo;
import run.hxtia.workbd.service.admin.OrganizationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/organizations")
@Api(tags = "OrganizationController")
@Tag(name = "OrganizationController", description = "组织模块")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService orgService;

    @GetMapping("/searchList")
    @ApiOperation("获取所有组织信息")
    public DataJsonVo<List<OrganizationVo>> searchOrgList() {
        return JsonVos.ok(Streams.map(orgService.list(), MapStructs.INSTANCE::po2vo));
    }

    @PostMapping("/searchListPage")
    @ApiOperation("获取所有组织信息【分页】")
    public PageJsonVo<Organization> searchOrgPageList(@RequestBody OrganizationPageReqVo pageReqVo) {
        return JsonVos.ok(orgService.list(pageReqVo));
    }

    @PostMapping("/update")
    @ApiOperation("更新组织信息")
    public JsonVo update(@Valid @RequestBody OrganizationReqVo reqVo) throws Exception {
        if (orgService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
