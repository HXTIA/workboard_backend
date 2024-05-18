package run.hxtia.workbd.controller.admin.notificationwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.*;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.service.notificationwork.NotificationService;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */

@RestController
@RequestMapping("/admin/notificationWork/notification")
@RequiredArgsConstructor
@Api(tags = "NotificationController")
@Tag(name = "NotificationController", description = "【B端】后台通知管理模块")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/list")
    @ApiOperation(value = "分页查询通知")
    @RequiresPermissions(Constants.Permission.NOTICE_READ)
    public PageJsonVo<NotificationVo> list(@RequestBody NotificationPageReqVo pageReqVo) {
        return JsonVos.ok(notificationService.listPage(pageReqVo, pageReqVo.getType()));
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或更新通知")
    @RequiresPermissions(Constants.Permission.NOTICE_CREATE)
    public JsonVo saveOrUpdate(@RequestBody NotificationReqVo reqVo) {
        if (notificationService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/removeByIds")
    @ApiOperation(value = "删除一条或多条通知【逻辑删除】")
    @RequiresPermissions(Constants.Permission.NOTICE_DELETE)
    public JsonVo removeByIds(@RequestParam String ids) {
        if (notificationService.removeByIds(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @GetMapping("/getByNotificationId")
    @ApiOperation(value = "根据通知ID获取通知信息")
    @RequiresPermissions(Constants.Permission.NOTICE_READ)
    public DataJsonVo<NotificationVo> getByNotificationId(@RequestParam Long notificationId) {
        return JsonVos.ok(notificationService.getByNotificationId(notificationId));
    }

    @PostMapping("/removeHistory")
    @ApiOperation(value = "删除一条或多条通知【彻底删除】")
    @RequiresPermissions(Constants.Permission.NOTICE_DELETE)
    public boolean removeHistory(@RequestParam String ids) {
        return notificationService.removeHistory(ids);
    }
}
