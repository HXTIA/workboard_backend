package run.hxtia.workbd.controller.admin.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.vo.request.course.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.request.course.CourseReqVo;
import run.hxtia.workbd.pojo.vo.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.response.course.CourseVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.notificationwork.CourseService;

import javax.validation.Valid;
import java.util.function.Function;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@RestController
@RequestMapping("/admin/notificationWorks/courses")
@Api(tags = "CourseController")
@Tag(name = "CourseController", description = "课程管理模块")
@RequiredArgsConstructor
public class CourseController extends BaseController<Course, CourseReqVo> {

    private final CourseService courseService;

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
    public CourseVo getCourseInfoById(@PathVariable Integer courseId) {
        return courseService.getCourseInfoById(courseId);
    }

    @GetMapping("/list")
    @ApiOperation("获取所有课程列表")
    public PageVo<CourseVo> getList() {
        return courseService.getList();
    }

    @DeleteMapping("/remove/{courseId}")
    @ApiOperation("删除课程")
    public JsonVo remove(@PathVariable Integer courseId) {
        if (courseService.delete(courseId)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
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
    public PageVo<CourseVo> getCourseInfoByCollegeIdWithPagination(@Valid @RequestBody CoursePageReqVo reqVo) {
        return courseService.getCourseInfoByCollegeIdWithPagination(reqVo.getCollegeId(), reqVo.getPage().intValue(), reqVo.getSize().intValue());
    }

    @Override
    protected IService<Course> getService() {
        return null;
    }

    @Override
    protected Function<CourseReqVo, Course> getFunction() {
        return null;
    }
}
