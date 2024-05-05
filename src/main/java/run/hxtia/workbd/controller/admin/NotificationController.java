package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.vo.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageJsonVo;
import run.hxtia.workbd.service.admin.NotificationService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/notifications")
@RequiredArgsConstructor
@Api(tags = "NotificationController")
@Tag(name = "NotificationController", description = "通知管理模块")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/api/notifications/{notification_id}")
    @ApiOperation("获取所有通知信息【分页】")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public PageJsonVo<NotificationVo> searchPageList(@RequestBody NotificationPageReqVo pageReqVo) {
        return JsonVos.ok(notificationService.list(pageReqVo, Constants.Status.WORK_ENABLE));
    }

    @PostMapping("/api/notifications")
    @ApiOperation("管理员或教师发布通知，可以定时或立即发送")
    //权限
    public JsonVo create(@Valid NotificationReqVo reqVo) throws Exception {
        if (notificationService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @GetMapping("/api/notifications/{notification_id}")
    @ApiOperation("用户根据通知ID查看具体的通知内容")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public DataJsonVo<NotificationVo> searchOne(@NotNull @RequestParam Long notificationId) {
        return JsonVos.ok(notificationService.getByNotificationId(notificationId));
    }

    // TODO: 增加一个历史作业的管理模块【删除、查询】

    @PostMapping("/api/notifications/{notification_id}")
    @ApiOperation("分页获取历史记录作业【分页】")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public PageJsonVo<NotificationVo> searchHistoryPageList(@RequestBody NotificationPageReqVo notificationPageReqVo) {
        return JsonVos.ok(notificationService.list(notificationPageReqVo, Constants.Status.WORK_DISABLE));
    }

    @PostMapping("/api/notifications/{notification_id}")
    @ApiOperation("管理员编辑已发布的通知内容")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_UPDATE)
    public JsonVo update(@Valid @RequestBody NotificationReqVo reqVo) throws Exception {
        if (reqVo.getNotification_id() == null || Integer.parseInt(reqVo.getNotification_id()) <= 0)
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        return create(reqVo);
    }


    @PostMapping("/remove")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_DELETE)
    @ApiOperation("删除一条或多条【多个ID间用逗号(,)隔开】(删除后可在历史记录中查看)")
    public JsonVo remove(@RequestParam @NotBlank(message = "ids是必传参数") String ids) {
        if (notificationService.removeByIds(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @PostMapping("/removeHistory")
    @ApiOperation("删除一条或多条【多个ID间用逗号(,)隔开】(永久删除)")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_DELETE)
    public JsonVo removeHistory(@NotBlank(message = "ID不能为空") @RequestParam String ids) {
        if (notificationService.removeHistory(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

}
