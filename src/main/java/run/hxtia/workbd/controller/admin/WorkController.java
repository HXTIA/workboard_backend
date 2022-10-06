package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.request.page.WorkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.WorkVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageJsonVo;
import run.hxtia.workbd.service.admin.WorkService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/admin/works")
@RequiredArgsConstructor
@Api(tags = "WorkController")
@Tag(name = "WorkController", description = "后台管理作业模块")
public class WorkController {

    private final WorkService workService;

    @PostMapping("/searchPageList")
    @ApiOperation("获取所有作业信息【分页】")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public PageJsonVo<WorkVo> searchPageList(@RequestBody WorkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_ENABLE));
    }

    @PostMapping("/create")
    @ApiOperation("新建作业")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_CREATE)
    public JsonVo create(@Valid WorkReqVo reqVo) throws Exception {
        if (workService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("编辑作业")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_UPDATE)
    public JsonVo update(@Valid @RequestBody WorkReqVo reqVo) throws Exception {
        if (reqVo.getId() == null || reqVo.getId() <= 0)
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        return create(reqVo);
    }

    @PostMapping("/editPictureUpload")
    @ApiOperation("多图片编辑，编辑作业的图片【支持多图片】")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_UPDATE)
    public JsonVo editPictureUpload(@Valid WorkUploadReqVo uploadReqVo) throws Exception {
        if (workService.updatePictures(uploadReqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/remove")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_DELETE)
    @ApiOperation("删除一条或多条【多个ID间用逗号(,)隔开】(删除后可在历史记录中查看)")
    public JsonVo remove(@RequestParam @NotBlank(message = "ids是必传参数") String ids) {
        if (workService.removeByIds(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @GetMapping("/searchOne")
    @ApiOperation("根据作业ID查询作业信息")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public DataJsonVo<WorkVo> searchOne(@NotNull @RequestParam Long workId) {
        return JsonVos.ok(workService.getByWorkId(workId));
    }

    // TODO: 增加一个历史作业的管理模块【删除、查询】

    @PostMapping("/searchHistoryPageList")
    @ApiOperation("分页获取历史记录作业【分页】")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public PageJsonVo<WorkVo> searchHistoryPageList(@RequestBody WorkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_DISABLE));
    }

    @PostMapping("/removeHistory")
    @ApiOperation("删除一条或多条【多个ID间用逗号(,)隔开】(永久删除)")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_DELETE)
    public JsonVo removeHistory(@NotBlank(message = "ID不能为空") @RequestParam String ids) {
        if (workService.removeHistory(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }
}
