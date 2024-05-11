package run.hxtia.workbd.controller.miniapp.notificationwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentNotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.notificationwork.NotificationService;
import run.hxtia.workbd.service.notificationwork.StudentNotificationService;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */


@RestController
@RequestMapping("/wx/notificationWork/notifyAndWork")
@RequiredArgsConstructor
@Api(tags = "NotificationWorksController")
@Tag(name = "NotificationWorksController", description = "小程序作业通知模块")
public class NotificationWorksController {

        // 作业
        private final HomeworkService workService;
        // 通知
        private final StudentNotificationService studentNotificationService;

        // 查看通知列表
        @PostMapping("/notifications")
        @ApiOperation("获取学生通知列表")
        public ResponseEntity<ExtendedPageVo<NotificationVo>> getStudentNotifications(
            @RequestBody StudentNotificationPageReqVo reqVo) {
            try {
                ExtendedPageVo<NotificationVo> notifications =  studentNotificationService.getNotificationListByStuId(reqVo);
                return ResponseEntity.ok(notifications);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }


        //查看作业列表

}
