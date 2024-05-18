package run.hxtia.workbd.controller.admin.notificationwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.HomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.HomeworkUploadReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.JsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentWorkPageReqVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
@RestController
@RequestMapping("/admin/notificationWork/works")
@RequiredArgsConstructor
@Api(tags = "WorkController")
@Tag(name = "WorkController", description = "【B端】后台作业管理模块")
public class HomeWorkController {

    private final HomeworkService workService;

    @PostMapping("/searchPageList")
    @ApiOperation("获取所有作业信息【分页】")
    @RequiresPermissions(Constants.Permission.HOMEWORK_READ)
    public PageJsonVo<HomeworkVo> searchPageList(@RequestBody HomeworkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_ENABLE));
    }

    @PostMapping("/create")
    @ApiOperation("新建作业")
    @RequiresPermissions(Constants.Permission.HOMEWORK_CREATE)
    public JsonVo create(@Valid HomeworkReqVo reqVo) throws Exception {
        // 填充请求信息
        reqVo.fillInfo(Constants.Status.PUBLISH_PLAT_WEB, "");

        if (workService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("编辑作业")
    @RequiresPermissions(Constants.Permission.HOMEWORK_UPDATE)
    public JsonVo update(@Valid @RequestBody HomeworkReqVo reqVo) throws Exception {
        if (reqVo.getId() == null || reqVo.getId() <= 0)
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        return create(reqVo);
    }

    @PostMapping("/editPictureUpload")
    @ApiOperation("多图片编辑，编辑作业的图片【支持多图片】")
    @RequiresPermissions(Constants.Permission.HOMEWORK_UPDATE)
    public JsonVo editPictureUpload(@Valid HomeworkUploadReqVo uploadReqVo) throws Exception {
        if (workService.updatePictures(uploadReqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/remove")
    @RequiresPermissions(Constants.Permission.HOMEWORK_DELETE)
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
    @RequiresPermissions(Constants.Permission.HOMEWORK_READ)
    public DataJsonVo<HomeworkVo> searchOne(@NotNull @RequestParam Long workId) {
        return JsonVos.ok(workService.getByWorkId(workId));
    }

    // TODO: 增加一个历史作业的管理模块【删除、查询】

    @PostMapping("/searchHistoryPageList")
    @ApiOperation("分页获取历史记录作业【分页】")
    @RequiresPermissions(Constants.Permission.HOMEWORK_READ)
    public PageJsonVo<HomeworkVo> searchHistoryPageList(@RequestBody HomeworkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_DISABLE));
    }

    @PostMapping("/removeHistory")
    @ApiOperation("删除一条或多条【多个ID间用逗号(,)隔开】(永久删除)")
    @RequiresPermissions(Constants.Permission.HOMEWORK_DELETE)
    public JsonVo removeHistory(@NotBlank(message = "ID不能为空") @RequestParam String ids) {
        if (workService.removeHistory(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @PostMapping("/searchWorkByStuId")
    @ApiOperation("根据学生 ID 查询作业信息，stuId != null")
    public DataJsonVo<List<StudentHomeworkDetailDto>> searchStuWorkListByStuId(@RequestBody StudentWorkPageReqVo reqVo) {
        return JsonVos.ok(workService.getWorkInfoListByStuId(reqVo));
    }
}
