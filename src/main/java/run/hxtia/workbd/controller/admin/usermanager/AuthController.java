package run.hxtia.workbd.controller.admin.usermanager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassIdReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassInfoReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */

@RestController
@RequestMapping("/admin/userManager/auth")
@Api(tags = "AuthController")
@Tag(name = "AuthController", description = "【B端】授权模块")
@RequiredArgsConstructor
public class  AuthController {

    private final AuthorizationService authorizationService;

    // 根据权限资源，展示能授权的 课程列表，班级列表
    @PostMapping("/getListByPermission")
    @ApiOperation("通过权限资源，获取课程，班级")
    @RequiresPermissions(Constants.Permission.AUTHORIZE_READ)
    public DataJsonVo<CourseAndClassVo> getListByPermission(HttpServletRequest request) {

        return JsonVos.ok(authorizationService.getCourseAndClasslistByAuth(request.getHeader(Constants.Web.HEADER_TOKEN)));
    }

    // 根据课程id，和班级id 列表生成code
    @PostMapping("/generateCode")
    @ApiOperation("通过课程id和班级id，生成授权码")
    @RequiresPermissions(Constants.Permission.AUTHORIZE_CREATE)
    public DataJsonVo<String> generateCode(@Valid @RequestBody AuthCourseAndClassIdReqVo reqVo,HttpServletRequest request) {
        return JsonVos.ok(authorizationService.generateSelectionCode(reqVo,request.getHeader(Constants.Web.HEADER_TOKEN)),"授权码Code生成成功");
    }

    // 根据课程信息列表，班级信息列表，生成授权码
    @PostMapping("/generateCodeByCourseAndClass")
    @ApiOperation("通过课程信息和班级信息，生成授权码")
    public DataJsonVo<String> generateCodeByCourseAndClass(@Valid @RequestBody AuthCourseAndClassInfoReqVo reqVo,HttpServletRequest request) {
        return JsonVos.ok(authorizationService.generateCodeByCourseAndClassInfo(reqVo,request.getHeader(Constants.Web.HEADER_TOKEN)),"授权码Code生成成功");
    }



}
