package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.AdminUserRoleMapper;
import run.hxtia.workbd.pojo.po.AdminUserRole;
import run.hxtia.workbd.service.admin.AdminUserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 用户角色模块 业务层
 */
@Service
public class AdminUserRoleServiceImpl
    extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements AdminUserRoleService {

    /**
     * 根据用户ID，删除所有的角色信息
     * @param userId：用户ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByUserId(Long userId) {
        if (userId == null || userId <= 0) return false;
        LambdaQueryWrapper<AdminUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserRole::getUserId, userId);
        return remove(wrapper);
    }

    /**
     * 添加角色信息
     * @param userId：用户ID
     * @param roleIdsStr：角色ID【多个角色用 逗号, 隔开】
     * @return ：是否成功
     */
    @Override
    public boolean save(Long userId, String roleIdsStr) {
        if (!StringUtils.hasLength(roleIdsStr)) return false;

        // 构建用户角色信息
        String[] roleIds = roleIdsStr.split(",");

        List<AdminUserRole> adminUserRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            adminUserRoles.add(new AdminUserRole(userId, Short.valueOf(roleId)));
        }

        return saveBatch(adminUserRoles);
    }

    /**
     * 根据用户ID获取角色ID
     * @param userId：用户ID
     * @return ：该用户的所有角色ID
     */
    @Override
    public List<Short> listRoleIds(Integer userId) {
        if (userId == null || userId <= 0) return null;

        LambdaQueryWrapper<AdminUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(AdminUserRole::getRoleId).eq(AdminUserRole::getUserId, userId);

        // 查询角色ID
        List<Object> roleIds = baseMapper.selectObjs(wrapper);

        return Streams.map(roleIds, roleId -> ((Integer) roleId).shortValue());
    }

    /**
     * 根据角色ID获取用户ID
     * @param roleId：用户ID
     * @return ：该角色的所有用户ID
     */
    @Override
    public List<Long> listAdminUserIds(Short roleId) {
        if (roleId == null || roleId <= 0) return null;
        MpLambdaQueryWrapper<AdminUserRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(AdminUserRole::getUserId).eq(AdminUserRole::getRoleId, roleId);
        List<Object> userIds = baseMapper.selectObjs(wrapper);
        return Streams.map(userIds, (userId) -> (Long) userId);
    }
}
