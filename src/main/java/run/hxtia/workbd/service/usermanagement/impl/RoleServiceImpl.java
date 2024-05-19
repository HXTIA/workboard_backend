package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.mapper.RoleMapper;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.po.RoleResource;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.RoleReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.RoleVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.service.usermanagement.AdminUserRoleService;
import run.hxtia.workbd.service.usermanagement.RoleResourceService;
import run.hxtia.workbd.service.usermanagement.RoleService;

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
     * @param pageReqVo ：分页参数
     * @param token：用户token
     * @return ：分页后的角色列表
     */
    @Override
    public PageVo<RoleVo> list(RolePageReqVo pageReqVo, String token) {

        // 构建查询条件
        MpLambdaQueryWrapper<Role> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Role::getName, Role::getIntro).
        eq(Role::getCollegeId, Redises.getClgIdByToken(token));

        // 构建分页结果
        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper)
            .buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 新建|保存角色
     * @param reqVo ：角色信息
     * @param token ：用户token
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(RoleReqVo reqVo, String token) {
        Role po = MapStructs.INSTANCE.reqVo2po(reqVo);
        Short id = reqVo.getId();
        if (id == null || id <= 0) {
            // 设置组织ID
            po.setCollegeId(Redises.getClgIdByToken(token));
        }

        // 保存角色信息
        if (!saveOrUpdate(po)) return false;

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

    /**
     * 获取所有组织内角色
     * @param token：用户Token
     * @return ：组织内角色
     */
    @Override
    public List<Role> list(String token) {

        // 通过组织ID过滤一下
        MpLambdaQueryWrapper<Role> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Role::getCollegeId, Redises.getClgIdByToken(token));

        return list(wrapper);
    }
}
