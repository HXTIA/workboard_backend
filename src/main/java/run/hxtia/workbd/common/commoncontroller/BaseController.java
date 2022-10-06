package run.hxtia.workbd.common.commoncontroller;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.JsonVo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 1、公共的controller【删除、更新、添加】
 * 2、这里面的只是最简单的 删、更、加
 * 3、若功能复杂，请重写对应方法
 * @param <Po>：数据库保存的Po对象
 * @param <ReqVo>：前端传过来的参数
 */
@Validated
public abstract class BaseController<Po, ReqVo> {
    /**
     * 获取对应的 Service
     */
    protected abstract IService<Po> getService();
    /**
     * 如何将 ReqVo -> Po
     */
    protected abstract Function<ReqVo, Po> getFunction();

    @PostMapping("/remove")
    @ApiOperation("删除一条或者多条数据【多个ID间用逗号(,)隔开】")
    public JsonVo remove(@NotBlank(message = "ids不能为空") @RequestParam String ids) {
        if (getService().removeByIds(Arrays.asList(ids.split(",")))) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @PostMapping("/create")
    @ApiOperation("添加")
    public JsonVo create(@Valid @RequestBody ReqVo reqVo) {
        // 将ReqVo -> Po
        Po po = getFunction().apply(reqVo);
        if (getService().save(po)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public JsonVo update(@Valid @RequestBody ReqVo reqVo) {
        // 将ReqVo -> Po
        Po po = getFunction().apply(reqVo);
        if (getService().updateById(po)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

}
