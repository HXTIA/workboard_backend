package run.hxtia.workbd.controller.admin.notificationwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.*;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.SaveCoursesAndHomeworksReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;

import javax.validation.Valid;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@RestController
@RequestMapping("/admin/notificationWork/courses")
@Api(tags = "CourseController")
@Tag(name = "CourseController", description = "【B端】课程管理模块")
@RequiredArgsConstructor
public class CourseController  {

    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @PostMapping("/create")
    @ApiOperation("创建课程")
    public JsonVo create(@Valid @RequestBody CourseReqVo reqVo) {
        if (courseService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("编辑课程")
    public JsonVo edit(@Valid @RequestBody CourseEditReqVo reqVo) {
        if (courseService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @GetMapping("/{courseId}")
    @ApiOperation("根据ID获取课程信息")
    public DataJsonVo<CourseVo> getCourseInfoById(@PathVariable Integer courseId) {
        return JsonVos.ok(courseService.getCourseInfoById(courseId));
    }

    @GetMapping("/list")
    @ApiOperation("获取所有课程列表")
    // TODO：这个接口暂时用不到，到时候再修改返回值。
    public PageJsonVo<CourseVo> getList() {
        return JsonVos.ok(courseService.getList());
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除一条或者多条课程")
    public boolean remove(@RequestParam String ids) {
        return courseService.removeHistory(ids);
    }

    @PostMapping("/check")
    @ApiOperation("验证课程是否存在")
    public JsonVo checkCourseExists(@RequestParam String courseName, @RequestParam Integer collegeId) {
        boolean exists = courseService.checkCourseExists(courseName, collegeId);
        if (exists) {
            return JsonVos.ok(CodeMsg.CHECK_OK);
        } else {
            return JsonVos.error(CodeMsg.CHECK_ERROR);
        }
    }

    @PostMapping("/college/page")
    @ApiOperation("根据学院ID分页获取课程信息")
    public PageJsonVo<CourseVo> getCourseInfoByCollegeIdWithPagination(@Valid @RequestBody CoursePageReqVo reqVo) {
        return JsonVos.ok(courseService.getPage(reqVo));
    }


    @PostMapping("/saveCoursesAndHomeworks")
    @ApiOperation("批量保存学生课程和作业信息")
    public JsonVo saveCoursesAndHomeworks(@Valid @RequestBody SaveCoursesAndHomeworksReqVo reqVo) {
        if (studentCourseService.saveCoursesAndHomeworks(reqVo.getCourseIds(), reqVo.getStudentId())) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }
}
