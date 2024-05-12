package run.hxtia.workbd.controller.admin.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.Classes;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.organization.request.ClassEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.ClassReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.ClassPageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.JsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.service.organization.ClassService;

import javax.validation.Valid;
import java.util.function.Function;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
@RestController
@RequestMapping("/admin/organization/classes")
@Api(tags = "ClassController")
@Tag(name = "ClassController", description = "【B端】班级管理模块")
@RequiredArgsConstructor
public class ClassController extends BaseController<Classes, ClassReqVo> {

    private final ClassService classService;

    // 创建班级
    @PostMapping("/create")
    @ApiOperation("创建班级")
    public JsonVo create(@Valid @RequestBody ClassReqVo reqVo) {
        Integer gradeId = Integer.parseInt(reqVo.getGradeId());
        if (classService.save(reqVo, gradeId)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 编辑班级
    @PostMapping("/edit")
    @ApiOperation("编辑班级")
    public JsonVo edit(@Valid @RequestBody ClassEditReqVo reqVo) {
        if (classService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 根据ID获取班级信息
    @GetMapping("/{id}")
    @ApiOperation("根据ID获取班级信息")
    public ClassVo getClassInfoById(@PathVariable Integer id) {
        return classService.getClassInfoById(id);
    }

    // 获取所有班级列表
    @GetMapping("/list")
    @ApiOperation("获取所有班级列表")
    public PageVo<ClassVo> getList() {
        return classService.getList();
    }

    // 删除班级
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除班级")
    public JsonVo remove(@PathVariable Integer id) {
        if (classService.delete(id)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @Override
    protected IService<Classes> getService() {
        return classService;
    }

    @Override
    protected Function<ClassReqVo, Classes> getFunction() {
        return null;
    }

    @GetMapping("/check/{id}")
    @ApiOperation("验证班级是否存在")
    public JsonVo checkClassInfo(@PathVariable Integer id) {
        boolean exists = classService.checkClassInfo(id);
        if (exists) {
            return JsonVos.ok(CodeMsg.CHECK_OK);
        } else {
            return JsonVos.error(CodeMsg.CHECK_ERROR);
        }
    }

    @PostMapping("/grade/page")
    @ApiOperation("根据年级ID分页获取班级信息")
    public PageJsonVo<ClassVo> getClassInfoByGradeIdWithPagination(@Valid @RequestBody ClassPageReqVo reqVo) {
        return JsonVos.ok(classService.listPage(reqVo));
    }
}
