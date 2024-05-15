package run.hxtia.workbd.controller.admin.usermanager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CourseAndClassByAuthReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;

import javax.servlet.http.HttpServletRequest;

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
    public DataJsonVo<CourseAndClassVo> getListByPermission(HttpServletRequest request) {

        return JsonVos.ok(authorizationService.getCourseAndClasslistByAuth(request.getHeader(Constants.Web.HEADER_TOKEN)));
    }


}
