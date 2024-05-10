package run.hxtia.workbd.controller.miniapp.usermanagement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.vo.request.save.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.request.save.StudentReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.miniapp.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/user/student")
@RequiredArgsConstructor
@Api(tags = "WxUserController")
@Tag(name = "WxUserController", description = "小程序用户模块")
public class WxUserController {
    private final StudentService studentService;

    @GetMapping("/getToken")
    @ApiOperation("根据 code 获取 token")
    public DataJsonVo<String> getToken(@NotNull(message = "code 不能为空") String code) throws Exception {
        return JsonVos.ok(studentService.getToken(code), "Token 获取成功");
    }

    @GetMapping("/getStudent")
    @ApiOperation("获取微信用户的数据")
    public DataJsonVo<StudentInfoDto> getStudent(HttpServletRequest request) {
        return JsonVos.ok(studentService.getStudentByToken(request.getHeader(Constants.WxApp.WX_TOKEN)));
    }

    @GetMapping("/checkToken")
    @ApiOperation("获取微信用户的数据, 请求头：WXToken, 返回 code = 0 代表验证成功")
    public JsonVo checkToken(HttpServletRequest request) throws Exception {
        if (!studentService.checkToken(request.getHeader(Constants.WxApp.WX_TOKEN))) {
            return JsonVos.error(CodeMsg.CHECK_TOKEN_ERR);
        }
        return JsonVos.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑完善用户信息")
    public JsonVo update(@Valid @RequestBody StudentReqVo reqVo) {
        if (studentService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation("用户头像上传")
    public JsonVo uploadAvatar(@Valid StudentAvatarReqVo reqVo) throws Exception  {
        if (studentService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
