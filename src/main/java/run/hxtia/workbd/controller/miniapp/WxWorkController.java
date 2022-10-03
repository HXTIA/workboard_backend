package run.hxtia.workbd.controller.miniapp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.request.page.base.WxWorkPageReqVo;
import run.hxtia.workbd.pojo.vo.response.UserWorkVo;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.PageJsonVo;
import run.hxtia.workbd.service.miniapp.WxWorkService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wx/works")
@RequiredArgsConstructor
@Api(tags = "WxWorkController")
@Tag(name = "WxWorkController", description = "C端作业模块")
public class WxWorkController {

    private final WxWorkService workService;

    @GetMapping("/searchList")
    @ApiOperation("根据用户ID查询该用户的所有作业【现存】")
    public DataJsonVo<List<UserWorkVo>> searchList(@NonNull @RequestParam Long userId) {
        return JsonVos.ok(workService.list(userId, Constants.Status.WORK_ENABLE));
    }

    @GetMapping("/searchHistoryList")
    @ApiOperation("根据用户ID查询该用户的所有作业【历史】")
    public DataJsonVo<List<UserWorkVo>> searchHistoryList(@NonNull @RequestParam Long userId) {
        return JsonVos.ok(workService.list(userId, Constants.Status.WORK_DISABLE));
    }

    @PostMapping("/searchPageList")
    @ApiOperation("分页查询该用户的所有作业【现存、分页】")
    public PageJsonVo<UserWorkVo> searchPageList(@Valid @RequestBody WxWorkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_ENABLE));
    }

    @PostMapping("/searchHistoryPageList")
    @ApiOperation("分页查询该用户的所有作业【历史、分页】")
    public PageJsonVo<UserWorkVo> searchHistoryPageList(@Valid @RequestBody WxWorkPageReqVo pageReqVo) {
        return JsonVos.ok(workService.list(pageReqVo, Constants.Status.WORK_DISABLE));
    }

}
