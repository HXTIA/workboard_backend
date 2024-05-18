package run.hxtia.workbd.controller.miniapp.notificationwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.dto.AdminUserInfoDto;
import run.hxtia.workbd.pojo.vo.common.response.result.*;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.vo.notificationwork.request.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.SaveCoursesAndHomeworksReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentHomeworkReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentNotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.organization.request.GradeEditReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentWorkPageReqVo;
import run.hxtia.workbd.service.notificationwork.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private final StudentCourseService studentCourseService;

    private final StudentHomeworkService studentHomeworkService;

    // 查看通知列表   已经解决
    @PostMapping("/notifications")
    @ApiOperation("获取学生通知列表")
    public PageJsonVo<NotificationVo> getStudentNotifications(@RequestBody StudentNotificationPageReqVo reqVo) {
            PageVo<NotificationVo> notifications =  studentNotificationService.getNotificationListByStuId(reqVo);
            return JsonVos.ok(notifications);
    }

    // 查看作业列表
    @PostMapping("/searchWorks")
    @ApiOperation("获取学生作业")
    public PageJsonVo<StudentHomeworkDetailDto> searchStuWorkList(@RequestBody StudentWorkPageReqVo reqVo, HttpServletRequest request) {
        reqVo.setWxToken(request.getHeader(Constants.WxMiniApp.WX_TOKEN));
        return JsonVos.ok(workService.getWorkInfoListByStuToken(reqVo));
    }

    @GetMapping("/{id}")
    @ApiOperation("通过作业 Id 获取作业详情信息")
    public DataJsonVo<StudentHomeworkDetailDto> getWorkInfo(@PathVariable @NotNull Long id, HttpServletRequest request) {
        return JsonVos.ok(workService.getWorkInfo(id, request.getHeader(Constants.WxMiniApp.WX_TOKEN)));
    }

    @PostMapping("/edit")
    @ApiOperation("编辑 pin or Status")
    public JsonVo edit(@Valid @RequestBody StudentHomeworkReqVo reqVo, HttpServletRequest request) {

        reqVo.setWxToken(request.getHeader(Constants.WxMiniApp.WX_TOKEN));
        if (studentHomeworkService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/saveCourses")
    @ApiOperation("批量保存学生课程")
    public JsonVo saveCoursesAndHomeworks(@Valid @RequestBody SaveCoursesAndHomeworksReqVo reqVo) {
        if (studentCourseService.saveCoursesAndHomeworks(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/publishWork")
    @ApiOperation("发布作业")
    public JsonVo create(@Valid HomeworkReqVo reqVo, HttpServletRequest request) throws Exception {
        // 标记发布平台为 WX 后再去保存
        reqVo.fillInfo(Constants.Status.PUBLISH_PLAT_WX, request.getHeader(Constants.WxMiniApp.WX_TOKEN));
        if (workService.saveOrUpdateFromWx(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
