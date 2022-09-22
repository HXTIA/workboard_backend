package run.hxtia.workbd.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.shiro.TokenFilter;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.admin.UserService;


@RestController
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户模块")
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    private final Redises redises;

    @Autowired
    UserController(UserService userService, Redises redises) {
        this.userService = userService;
        this.redises = redises;
    }

/*    @PostMapping("/login")
    @ApiOperation("登录")
    public DataJsonVo<LoginVo> login(@RequestBody LoginReqVo reqVo) {
        LoginVo user = (LoginVo) redises.get("user");
        System.out.println(user + "+++++++++++++\n");
        redises.del("user");
        return JsonVos.ok(userService.login(reqVo));
    }*/

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@RequestHeader(Constants.Web.HEADER_TOKEN) String token) {
        // 清空缓存中的token就可以了
        Caches.remove(token);
        return JsonVos.ok();
    }

}
