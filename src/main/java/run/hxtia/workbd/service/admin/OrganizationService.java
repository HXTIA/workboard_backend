package run.hxtia.workbd.service.admin;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Organization;

@Transactional(readOnly = true)
public interface OrganizationService extends IService<Organization> {

    /**
     * 检查组织信息
     * @param id ：组织ID
     * @return ：是否完善组织信息
     */
    boolean checkOrgInfo(Short id);

    /**
     * 注册默认的组织
     * @param organization ：组织信息
     * @return ：是否成功
     */
    boolean saveDefaultRegister(Organization organization);

}
