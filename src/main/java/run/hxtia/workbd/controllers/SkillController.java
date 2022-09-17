package run.hxtia.workbd.controllers;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.baseController.BaseController;
import run.hxtia.workbd.common.mapStruct.MapStructs;
import run.hxtia.workbd.common.utils.JsonVos;
import run.hxtia.workbd.common.utils.Streams;
import run.hxtia.workbd.pojo.po.Skill;
import run.hxtia.workbd.pojo.vo.request.save.SkillReqVo;
import run.hxtia.workbd.pojo.vo.response.SkillVo;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.services.SkillService;

import java.util.List;
import java.util.function.Function;

@RestController
@Api(tags = "SkillController")
@Tag(name = "SkillController", description = "技巧模块")
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController extends BaseController<Skill, SkillReqVo> {

    private final SkillService skillService;

    @ApiOperation("用MybatisPlus查询skill")
    @GetMapping("/list")
    public DataJsonVo<List<SkillVo>> list() {
        return JsonVos.ok(Streams.map(skillService.list(), MapStructs.INSTANCE::po2vo));
    }

    @ApiOperation("手写映射文件查询skills")
    @GetMapping("/testMapper")
    public List<Skill> testMapper() {
        return skillService.testMapper();
    }

    @Override
    protected IService<Skill> getService() {
        return skillService;
    }

    @Override
    protected Function<SkillReqVo, Skill> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
