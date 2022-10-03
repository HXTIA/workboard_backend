package run.hxtia.workbd.controller.miniapp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.request.save.UserWorkReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.miniapp.WxUserWorkService;

import javax.validation.Valid;

@RestController
@RequestMapping("/wx/userWork")
@RequiredArgsConstructor
@Api(tags = "WxUserWorkController")
@Tag(name = "WxUserWorkController", description = "C端用户作业模块")
public class WxUserWorkController {

    private final WxUserWorkService userWorkService;

    @PostMapping("/update")
    @ApiOperation("置顶作业、修改作业状态")
    public JsonVo update(@Valid @RequestBody UserWorkReqVo reqVo) {
        if (userWorkService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
