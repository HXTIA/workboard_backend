package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.RoleResourceMapper;
import run.hxtia.workbd.pojo.po.RoleResource;
import run.hxtia.workbd.service.usermanagement.RoleResourceService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleResourceServiceImpl
    extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {


    /**
     * 根据角色ID查询所有的资源
     * @param roleIds ：角色ID
     * @return ：拥有资源的并集
     */
    @Override
    public List<Short> listResourceIds(List<Short> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return null;

        // 构建SQL
        MpLambdaQueryWrapper<RoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(RoleResource::getResourceId).in(RoleResource::getRoleId, roleIds);
        List<Object> res = baseMapper.selectObjs(wrapper);

        // 去重
        Set<Object> resIds = new HashSet<>(res);
        return Streams.list2List(resIds, (resId) -> ((Integer) resId).shortValue());
    }

    /**
     * 根据角色ID删除所有资源
     * @param roleId：角色ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByRoleId(Short roleId) {
        if (roleId == null || roleId <= 0) return false;

        MpLambdaQueryWrapper<RoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(RoleResource::getRoleId, roleId);

        return baseMapper.delete(wrapper) > 0;
    }
}
