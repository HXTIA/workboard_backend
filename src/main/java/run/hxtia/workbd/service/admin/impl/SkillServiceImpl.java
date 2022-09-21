package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.mapper.SkillMapper;
import run.hxtia.workbd.pojo.po.entity.Skill;
import run.hxtia.workbd.service.admin.SkillService;

import java.util.List;


@Service
public class SkillServiceImpl
    extends ServiceImpl<SkillMapper, Skill> implements SkillService {

    /**
     * 这是一个测试方法自己写 xml文件的方法
     */
    @Override
    public List<Skill> testMapper() {
        return baseMapper.testMapper();
    }
}
