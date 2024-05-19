package run.hxtia.workbd.controller.admin.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.Grade;
import run.hxtia.workbd.pojo.vo.common.response.result.*;
import run.hxtia.workbd.pojo.vo.organization.request.GradeEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.GradeReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.GradePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.service.organization.GradeService;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
@RestController
@RequestMapping("/admin/organization/grades")
@Api(tags = "GradeController")
@Tag(name = "GradeController", description = "【B端】年级管理模块")
@RequiredArgsConstructor
public class GradeController extends BaseController<Grade, GradeReqVo> {

    private final GradeService gradeService;

    // 创建年级
    @PostMapping("/create")
    @ApiOperation("创建年级")
    @RequiresPermissions(Constants.Permission.GRADE_CREATE)
    public JsonVo create(@Valid @RequestBody GradeReqVo reqVo) {
        if (gradeService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 编辑年级
    @PostMapping("/edit")
    @ApiOperation("编辑年级")
    @RequiresPermissions(Constants.Permission.GRADE_UPDATE)
    public JsonVo edit(@Valid @RequestBody GradeEditReqVo reqVo) {
        if (gradeService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 根据ID获取年级信息
    @GetMapping("/{id}")
    @ApiOperation("根据ID获取年级信息")
    @RequiresPermissions(Constants.Permission.GRADE_READ)
    public DataJsonVo<GradeVo> getCollegeInfoById(@PathVariable Integer id) {
        return JsonVos.ok(gradeService.getGradeInfoById(id));
    }

    // 获取所有年级列表
    @GetMapping("/list")
    @ApiOperation("获取所有年级列表")
    @RequiresPermissions(Constants.Permission.GRADE_READ)
    public PageJsonVo<GradeVo> getList() {
        return JsonVos.ok(gradeService.getList());
    }


    // 删除年级
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除年级")
    @RequiresPermissions(Constants.Permission.GRADE_DELETE)
    public JsonVo remove(@PathVariable Integer id) {
        if (gradeService.removeById(id)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @GetMapping("/check")
    @ApiOperation("验证年级是否存在")
    @RequiresPermissions(Constants.Permission.GRADE_READ)
    public JsonVo checkGradeExists(@Valid @RequestBody GradeReqVo reqVo) {
        boolean exists = gradeService.checkGradeExists(reqVo.getName(), reqVo.getCollegeId());
        if (exists) {
            return JsonVos.ok(CodeMsg.CHECK_OK);
        } else {
            return JsonVos.error(CodeMsg.CHECK_ERROR);
        }
    }

    @GetMapping("/college/{collegeId}")
    @ApiOperation("根据学院ID获取年级信息")
    @RequiresPermissions(Constants.Permission.GRADE_READ)
    public DataJsonVo<List<GradeVo>> getGradeInfoByCollegeId(@PathVariable Integer collegeId) {
        return JsonVos.ok(gradeService.getGradeInfoByCollegeId(collegeId));
    }

    @PostMapping("/college/page")
    @ApiOperation("根据学院ID分页获取年级信息")
    @RequiresPermissions(Constants.Permission.GRADE_READ)
    public PageJsonVo<GradeVo> getGradeInfoByCollegeIdWithPagination(@Valid @RequestBody GradePageReqVo reqVo) {
        return JsonVos.ok(gradeService.getGradeInfoByCollegeIdWithPagination(reqVo));
    }

    @Override
    protected IService<Grade> getService() {
        return null;
    }

    @Override
    protected Function<GradeReqVo, Grade> getFunction() {
        return null;
    }
}
