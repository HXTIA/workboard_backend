package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.mapper.OrganizationMapper;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.service.admin.OrganizationService;

/**
 * 组织模块业务层
 */
@Service
public class OrganizationServiceImpl
    extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

}
