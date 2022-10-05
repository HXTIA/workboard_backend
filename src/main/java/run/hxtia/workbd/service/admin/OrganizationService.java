package run.hxtia.workbd.service.admin;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.vo.request.page.OrganizationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.OrganizationReqVo;
import run.hxtia.workbd.pojo.vo.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;

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
    @Transactional(readOnly = false)
    boolean saveDefaultRegisterOrg(Organization organization);

    /**
     * 分页查询组织信息
     * @param pageReqVo：查询参数
     * @return ：分页结果
     */
    PageVo<Organization> list(OrganizationPageReqVo pageReqVo);

    /**
     * 修改组织信息
     * @param reqVo：组织信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(OrganizationReqVo reqVo) throws Exception;

    /**
     * 判断组织是否存在
     * @param id ：组织ID
     * @return ：是否存在
     */
    boolean isExist(Short id);

    /**
     *
     * @param orgId :组织id
     * @return : 该id的组织信息
     */
    OrganizationVo queryOrgById(short orgId);
}
