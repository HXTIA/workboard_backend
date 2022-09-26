package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.mapper.OrganizationMapper;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.admin.OrganizationService;

/**
 * 组织模块业务层
 */
@Service
public class OrganizationServiceImpl
    extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {


    /**
     * 检查组织信息
     * @param id ：组织ID
     * @return ：是否完善组织信息
     */
    @Override
    public boolean checkOrgInfo(Short id) {
        if (id == null || id <= 0)
            return JsonVos.raise(CodeMsg.NO_ORG_INFO);
        Organization organization = baseMapper.selectById(id);

        // 没有对应的组织
        if (organization == null)
            return JsonVos.raise(CodeMsg.NO_ORG_INFO);

        // 验证组织名称是否修改
        if (!StringUtils.hasLength(organization.getName()))
            return JsonVos.raise(CodeMsg.PERFECT_ORG_INFO);

        return true;
    }

    /**
     * 注册默认的组织
     * @param organization ：组织信息
     * @return ：是否成功
     */
    @Override
    public boolean saveDefaultRegisterOrg(Organization organization) {
        if (organization == null) return false;
        return baseMapper.insertDefaultRegisterOrg(organization) > 0;
    }

}
