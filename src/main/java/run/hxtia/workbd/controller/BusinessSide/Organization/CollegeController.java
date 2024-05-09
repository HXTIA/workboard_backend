package run.hxtia.workbd.controller.BusinessSide.Organization;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.request.organization.CollegeEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.request.page.base.PageReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.CollegeVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.Organization.CollegeService;

import javax.validation.Valid;
import java.util.function.Function;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
@RestController
@RequestMapping("/B/organization/colleges")
@Api(tags = "CollegeController")
@Tag(name = "CollegeController", description = "【B端】学院管理模块")
@RequiredArgsConstructor
public class CollegeController extends BaseController<College, CollegeReqVo> {

    private final CollegeService collegeService;

    // 创建学院
    @PostMapping("/create")
    @ApiOperation("创建学院")
    public JsonVo create(@Valid @RequestBody CollegeReqVo reqVo) {
        if (collegeService.save(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    // 编辑学院
    @PostMapping("/edit")
    @ApiOperation("编辑学院")
    public JsonVo edit(@Valid @RequestBody CollegeEditReqVo reqVo) {
        if (collegeService.update(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }


    // 根据ID获取学院信息
    @GetMapping("/{id}")
    @ApiOperation("根据ID获取学院信息")
    public CollegeVo getCollegeInfoById(@PathVariable Integer id) {
        return collegeService.getCollegeInfoById(id);
    }

    // 获取所有学院列表
    @GetMapping("/list")
    @ApiOperation("获取所有学院列表")
    public PageVo<CollegeVo> getList() {
        return collegeService.getList();
    }

    /**
     * 分页获取学院信息
     * @param reqVo ：分页请求参数
     * @return 分页的学院信息
     */
    @PostMapping("/listPage")
    @ApiOperation("分页获取学院信息")
    public PageVo<CollegeVo> getPageList(@Valid @RequestBody PageReqVo reqVo) {
        return collegeService.getPageList(reqVo);
    }


    // 删除学院
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除学院")
    public JsonVo remove(@PathVariable Integer id) {
        if (collegeService.removeById(id)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @Override
    protected IService<College> getService() {
        return collegeService;
    }

    @Override
    protected Function<CollegeReqVo, College> getFunction() {
        return null;
    }

    @GetMapping("/check/{id}")
    @ApiOperation("验证学院是否存在")
    public JsonVo checkClgInfo(@PathVariable Integer id) {
        boolean exists = collegeService.checkClgInfo(id);
        if (exists) {
            return JsonVos.ok(CodeMsg.CHECK_OK);
        } else {
            return JsonVos.error(CodeMsg.CHECK_ERROR);
        }
    }

    @PostMapping("/register")
    @ApiOperation("注册默认学院")
    public JsonVo saveDefaultRegisterClg(@Valid @RequestBody College collegeInfo) {
        boolean saved = collegeService.saveDefaultRegisterClg(collegeInfo);
        if (saved) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
