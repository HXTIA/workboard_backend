package run.hxtia.workbd.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.mappers.SkillMapper;
import run.hxtia.workbd.pojo.po.Skill;
import run.hxtia.workbd.services.SkillService;

import java.util.List;


@Service
public class SkillServiceImpl
    extends ServiceImpl<SkillMapper, Skill> implements SkillService {

    @Override
    public List<Skill> testMapper() {
        return baseMapper.testMapper();
    }
}
