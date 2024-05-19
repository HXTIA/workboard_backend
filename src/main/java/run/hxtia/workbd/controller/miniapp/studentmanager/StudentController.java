package run.hxtia.workbd.controller.miniapp.studentmanager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.JsonVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;
import run.hxtia.workbd.service.usermanagement.StudentAuthorizationService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/studentManager/student")
@RequiredArgsConstructor
@Api(tags = "WxUserController")
@Tag(name = "WxUserController", description = "小程序用户模块")
public class StudentController {
    private final StudentService studentService;

    private final AuthorizationService authorizationService;

    @GetMapping("/getToken")
    @ApiOperation("根据 code 获取 token")
    public DataJsonVo<String> getToken(@NotNull(message = "code 不能为空") String code) throws Exception {
        return JsonVos.ok(studentService.getToken(code), "Token 获取成功");
    }

    @GetMapping("/getStudent")
    @ApiOperation("获取微信用户的数据")
    public DataJsonVo<StudentInfoDto> getStudent(HttpServletRequest request) {
        return JsonVos.ok(studentService.getStudentByToken(request.getHeader(Constants.WxMiniApp.WX_TOKEN)));
    }

    @GetMapping("/checkToken")
    @ApiOperation("获取微信用户的数据, 请求头：WXToken, 返回 code = 0 代表验证成功")
    public JsonVo checkToken(HttpServletRequest request) throws Exception {
        if (!studentService.checkToken(request.getHeader(Constants.WxMiniApp.WX_TOKEN))) {
            return JsonVos.error(CodeMsg.CHECK_TOKEN_ERR);
        }
        return JsonVos.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑完善用户信息")
    public JsonVo update(@Valid @RequestBody StudentReqVo reqVo, HttpServletRequest request) {
        if (studentService.update(reqVo, request.getHeader(Constants.WxMiniApp.WX_TOKEN))) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation("用户头像上传")
    public JsonVo uploadAvatar(@Valid StudentAvatarReqVo reqVo, HttpServletRequest request) throws Exception  {
        if (studentService.update(reqVo, request.getHeader(Constants.WxMiniApp.WX_TOKEN))) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 新增的端点
    @PostMapping("/verificationCode")
    @ApiOperation("核销授权码,核销成功会返回课程和班级信息")
    public DataJsonVo<CourseAndClassVo> verificationCode(@RequestParam @NotNull(message = "code 不能为空") String code, HttpServletRequest request) {
        String token = request.getHeader(Constants.WxMiniApp.WX_TOKEN);
        CourseAndClassVo courseAndClassVo = authorizationService.verificationCode(code, token);
        return JsonVos.ok(courseAndClassVo);
    }


}
