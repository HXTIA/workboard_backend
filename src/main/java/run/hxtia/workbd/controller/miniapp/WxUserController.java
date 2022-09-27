package run.hxtia.workbd.controller.miniapp;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.UserInfoDto;
import run.hxtia.workbd.pojo.vo.request.WxAuthLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.UserAvatarReqVo;
import run.hxtia.workbd.pojo.vo.request.save.UserReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.miniapp.WxUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/users")
@RequiredArgsConstructor
@Api(tags = "WxUserController")
@Tag(name = "WxUserController", description = "小程序用户模块")
public class WxUserController {
    private final WxUserService wxUserService;

    @GetMapping("/getSessionId")
    @ApiOperation("根据 code 获取 token")
    public DataJsonVo<String> getSessionId(@NotNull(message = "code不能为空") String code) throws Exception {
        return JsonVos.ok(wxUserService.getSessionId(code), "Token获取成功");
    }

    @PostMapping("/loginAuth")
    @ApiOperation("获取微信用户的数据")
    public DataJsonVo<UserInfoDto> loginAuth(@RequestBody WxAuthLoginReqVo wxAuth, HttpServletRequest request) {
        return JsonVos.ok(wxUserService.authLogin(wxAuth, request.getHeader("Token")));
    }

    @PostMapping("/update")
    @ApiOperation("编辑完善用户信息")
    public JsonVo update(@Valid @RequestBody UserReqVo reqVo) {
        if (wxUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation("用户头像上传")
    public JsonVo uploadAvatar(@Valid UserAvatarReqVo reqVo) throws Exception  {
        if (wxUserService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
