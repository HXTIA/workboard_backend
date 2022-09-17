package run.hxtia.workbd.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Skill;

import java.util.List;


/**
 * 持久层：Skill表
 */
@Repository
public interface SkillMapper extends BaseMapper<Skill> {
    List<Skill> testMapper();
}
