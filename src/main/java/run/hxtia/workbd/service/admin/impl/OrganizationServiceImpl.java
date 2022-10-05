package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.mapper.OrganizationMapper;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.vo.request.page.OrganizationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.OrganizationReqVo;
import run.hxtia.workbd.pojo.vo.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;
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

    /**
     * 分页查询组织信息
     * @param pageReqVo：查询参数
     * @return ：分页结果。目前组织里没有特殊数据，所以返回Organization就行
     */
    @Override
    public PageVo<Organization> list(OrganizationPageReqVo pageReqVo) {

        // 查询条件
        MpLambdaQueryWrapper<Organization> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Organization::getName).
            between(pageReqVo.getCreatedTime(), Organization::getCreatedAt);

        /*
        分页查询
        【因为这里不需要转换，所以 buildVo() 不用传参。若需要转换类型，请传入如何转换的函数】
         */
        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo();
    }

    /**
     * 修改组织信息
     * @param reqVo：组织信息
     * @return ：是否成功
     */
    @Override
    public boolean update(OrganizationReqVo reqVo) throws Exception {
        Organization po = MapStructs.INSTANCE.reqVo2po(reqVo);

        return Uploads.uploadOneWithPo(po,
            new UploadReqParam(reqVo.getBackground(),
                reqVo.getBackgroundFile()),
            baseMapper, Organization::setBackground);
    }

    /**
     * 判断组织是否存在
     * @param id ：组织ID
     * @return ：是否存在
     */
    @Override
    public boolean isExist(Short id) {
        if (id == null || id <= 0) return false;
        return baseMapper.selectById(id) != null;
    }

    @Override
    public OrganizationVo queryOrgById(short orgId) {
        Organization organization = baseMapper.selectById(orgId);
        OrganizationVo organizationVo = MapStructs.INSTANCE.po2vo(organization);
        System.out.println(organizationVo);
        return organizationVo;
    }
}
