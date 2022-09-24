package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Organization;

/**
 * 组织模块持久层
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    int defaultRegister(Organization po);

}
