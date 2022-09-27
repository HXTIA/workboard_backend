package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.vo.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.service.admin.OrganizationService;

import java.util.List;

@RestController
@RequestMapping("/admin/organizations")
@Api(tags = "OrganizationController")
@Tag(name = "OrganizationController", description = "组织模块")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService orgService;

    @GetMapping("/searchOrgList")
    @ApiOperation("获取所有组织信息")
    public DataJsonVo<List<OrganizationVo>> searchOrgList() {
        return JsonVos.ok(Streams.map(orgService.list(), MapStructs.INSTANCE::po2vo));
    }


}
