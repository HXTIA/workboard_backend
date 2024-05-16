package run.hxtia.workbd.controller.admin.notificationwork;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentCoursePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.JsonVo;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;

import javax.validation.Valid;
import java.util.List;

/**
 * 学生课程控制器
 * @author Xiaojin
 * @date 2024/5/7
 */
@RestController
@RequestMapping("/admin/notificationWork/studentCourses")
@Api(tags = "StudentCourseController")
@Tag(name = "StudentCourseController", description = "学生课程管理模块")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @PostMapping("/create")
    @ApiOperation("创建学生课程")
    public JsonVo create(@Valid @RequestBody StudentCourseReqVo reqVo) {
        if (studentCourseService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("编辑学生课程")
    public JsonVo edit(@Valid @RequestBody StudentCourseEditReqVo reqVo) {
        if (studentCourseService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @DeleteMapping("/remove/{studentId}/{courseId}")
    @ApiOperation("删除学生课程")
    public JsonVo remove(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        if (studentCourseService.delete(studentId, courseId)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @PostMapping("/{wechatId}")
    @ApiOperation("根据学生ID获取学生课程信息")
    public DataJsonVo<List<CourseVo>> getStudentCoursesByStudentId(@PathVariable String wechatId) {
        List<CourseVo> courses = studentCourseService.getStudentCoursesByStudentId(wechatId);
        return  JsonVos.ok(courses);
    }

    @PostMapping("/exists")
    @ApiOperation("检查学生课程是否存在")
    public JsonVo exists(@RequestParam Integer studentId, @RequestParam Integer courseId) {
        boolean exists = studentCourseService.exists(studentId, courseId);
        if (exists) {
            return JsonVos.ok(CodeMsg.CHECK_OK);
        } else {
            return JsonVos.error(CodeMsg.CHECK_ERROR);
        }
    }

    @PostMapping("/courses")
    @ApiOperation("根据学生ID获取分页的学生课程信息")
    public ResponseEntity<IPage<CourseVo>> getStudentCoursesByStudentIdWithPagination(@Valid @RequestBody StudentCoursePageReqVo reqVo) {
        IPage<CourseVo> courses = studentCourseService.getStudentCoursesByStudentIdWithPagination(reqVo.getWechatId(), new Page<>(reqVo.getPage(), reqVo.getSize()));
        return ResponseEntity.ok(courses);
    }



}
