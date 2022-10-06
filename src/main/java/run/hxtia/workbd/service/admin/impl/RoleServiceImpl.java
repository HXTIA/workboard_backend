package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.RoleMapper;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.po.RoleResource;
import run.hxtia.workbd.pojo.vo.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.RoleReqVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.admin.AdminUserRoleService;
import run.hxtia.workbd.service.admin.RoleResourceService;
import run.hxtia.workbd.service.admin.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl
    extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final Redises redises;
    private final AdminUserRoleService adminUserRoleService;
    private final RoleResourceService roleResourceService;

    /**
     * 角色分页列表
     * @param pageReqVo：分页参数
     * @return ：分页后的角色列表
     */
    @Override
    public PageVo<RoleVo> list(RolePageReqVo pageReqVo) {

        // 构建查询条件
        MpLambdaQueryWrapper<Role> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Role::getName, Role::getIntro);

        // 构建分页结果
        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper)
            .buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 新建|保存角色
     * @param reqVo：角色信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(RoleReqVo reqVo) {
        Role po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 保存角色信息
        if (!saveOrUpdate(po)) return false;

        Short id = reqVo.getId();

        if (id != null && id > 0) {
            // 来到这说明是编辑角色。将拥有该角色的用户全部找出
            List<Long> userIds = adminUserRoleService.listAdminUserIds(id);
            // 将所有拥有该角色的用户的用户踢下线
            redises.delByUserIds(userIds);
            // 删除角色的所有资源，方便重新添加资源信息
            roleResourceService.removeByRoleId(id);
        }


        // 为角色添加对应的资源
        List<Short> resourceIds = reqVo.getResourceIds();
        if (CollectionUtils.isEmpty(resourceIds)) return true;

        List<RoleResource> roleResources = new ArrayList<>();
        for (Short resourceId : resourceIds) {
            RoleResource temp = new RoleResource();
            temp.setRoleId(po.getId());
            temp.setResourceId(resourceId);
            roleResources.add(temp);
        }

        // 保存失败抛出异常，让其事务回滚
        if (!roleResourceService.saveBatch(roleResources))
            return JsonVos.raise(CodeMsg.SAVE_ERROR);

        return true;
    }

    /**
     * 根据用户ID查询角色列表
     * @param userId：用户ID
     * @return ：用户的角色列表
     */
    @Override
    public List<Role> listByUserId(Long userId) {
        if (userId == null || userId <= 0) return null;

        List<Short> roleIds = adminUserRoleService.listRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) return null;

        return baseMapper.selectBatchIds(roleIds);
    }
}
