package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.RoleMapper;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.request.page.RolePageReqVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.admin.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl
    extends ServiceImpl<RoleMapper, Role> implements RoleService {

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
}
