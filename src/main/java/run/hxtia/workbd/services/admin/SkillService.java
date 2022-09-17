package run.hxtia.workbd.services.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Skill;

import java.util.List;

/**
 * 业务层：skillService
 * 因为大部分业务都是查询的功能，所以直接将事务开启只读。
 * 若要修改数据库数据，请在对应接口@Transactional(readOnly = true)
 */
@Transactional(readOnly = true)
public interface SkillService extends IService<Skill> {

    List<Skill> testMapper();

}
