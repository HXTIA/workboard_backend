package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Organization;

/**
 * 组织模块持久层
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 用户注册时，给其注册默认的组织
     * @param po：空的组织信息
     * @return ：是否成功
     */
    int insertDefaultRegisterOrg(Organization po);

}
