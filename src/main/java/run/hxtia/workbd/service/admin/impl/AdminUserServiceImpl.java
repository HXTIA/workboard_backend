package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Md5s;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.mapper.AdminUserMapper;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.po.UserRole;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserEditReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserRegisterReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserReqVo;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.admin.AdminUserService;
import run.hxtia.workbd.service.admin.OrganizationService;
import run.hxtia.workbd.service.admin.UserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl
    extends ServiceImpl<AdminUserMapper, AdminUsers> implements AdminUserService {

    private final Redises redises;
    private final OrganizationService orgService;
    private final UserRoleService userRoleService;

    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     */
    @Override
    public AdminLoginVo login(AdminLoginReqVo reqVo) {

        //TODO: 加强LambdaQueryWrapper
        // 通过用户名查询user
        LambdaQueryWrapper<AdminUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUsers::getUsername, reqVo.getUsername());
        AdminUsers userPo = baseMapper.selectOne(wrapper);

        // 验证用户名
        if (userPo == null) {
            return JsonVos.raise(CodeMsg.WRONG_USERNAME);
        }

        // 验证密码
        boolean verify = Md5s.verify(reqVo.getPassword(), userPo.getSalt(), userPo.getPassword());
        if (!verify) {
            return JsonVos.raise(CodeMsg.WRONG_PASSWORD);
        }

        // 用户状态
        if (userPo.getStatus() == Constants.Users.UNABLE) {
            return JsonVos.raise(CodeMsg.USER_LOCKED);
        }

        // TODO：1、查询角色信息 2、查询角色资源信息 3、缓存到 Redis

        // 生成Token
        String token = Strings.getUUID();

        // 将对象其放入缓存中
        redises.set(Constants.Web.HEADER_TOKEN, token, userPo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        // 将用户Token 放入 缓存
        redises.set(String.valueOf(userPo.getId()), Constants.Web.HEADER_TOKEN + token);

        // 将 po -> vo
        AdminLoginVo vo = MapStructs.INSTANCE.po2loginVo(userPo);
        vo.setToken(token);

        return vo;
    }

    /**
     * 超管注册
     * @param reqVo：注册的信息
     * @return ：是否成功
     */
    @Override
    public boolean register(AdminUserRegisterReqVo reqVo) {

        // TODO: 验证验证码

        LambdaQueryWrapper<AdminUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUsers::getUsername, reqVo.getUsername());
        AdminUsers po = baseMapper.selectOne(wrapper);
        // 验证用户是否存在
        if (po != null) return JsonVos.raise(CodeMsg.EXIST_USERS);

        // 注册组织
        Organization defaultOrg = new Organization();
        if (orgService.saveDefaultRegister(defaultOrg)) {
            return JsonVos.raise(CodeMsg.REGISTER_ERROR);
        }

        // 密码加盐
        String salt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String psdWithMd5 = Md5s.md5(reqVo.getPassword(), salt);
        // 这里比较特殊，都得自己手动设置值
        po = MapStructs.INSTANCE.reqVo2po(reqVo);
        po.setSalt(salt);
        po.setPassword(psdWithMd5);
        po.setOrgId(defaultOrg.getId());

        // 若插入失败，得抛出异常，才能回滚事务
        boolean res = baseMapper.insert(po) > 0;
        if (!res) return JsonVos.raise(CodeMsg.OPERATE_OK);

        return true;
    }

    /**
     * 添加用户
     * @param reqVo ：用户信息
     * @return ：是否成功
     */
    @Override
    public boolean save(AdminUserReqVo reqVo) {

        AdminUsers po = MapStructs.INSTANCE.reqVo2po(reqVo);
        /*
        1、查询操作者信息组织ID
        2、检查组织信息
        3、将其置到 待建用户的组织ID
         */
        Short orgId = baseMapper.selectById(reqVo.getOperatorId()).getOrgId();
        if (!orgService.checkOrgInfo(orgId))
            return JsonVos.raise(CodeMsg.PERFECT_ORG_INFO);

        // 密码加盐
        String salt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String psdWithMd5 = Md5s.md5(reqVo.getPassword(), salt);
        po.setOrgId(orgId);
        po.setSalt(salt);
        po.setPassword(psdWithMd5);

        // 保存用户信息
        if (!save(po)) return false;

        // 说明没有分配角色信息，那么直接返回成功
        String roleIds = reqVo.getRoleIds();
        if (!StringUtils.hasLength(roleIds)) return true;

        return userRoleService.save(po.getId(), roleIds);
    }

    /**
     * 修改用户信息
     * @param reqVo ：用户信息
     * @return ：是否成功
     */
    public boolean update(AdminUserEditReqVo reqVo) {
        Long id = reqVo.getId();
        AdminUsers po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 将该用户的缓存清除掉【让其重新登陆】
        String userToken = (String) redises.get(String.valueOf(id));
        if (StringUtils.hasLength(userToken)) redises.del(userToken);

        // 清除该用户所有的角色
        userRoleService.removeByUserId(id);

        // 保存 or 编辑用户信息
        if (!updateById(po)) return false;

        String roleIds = reqVo.getRoleIds();
        // 说明没有分配角色信息，那么直接返回成功
        if (!StringUtils.hasLength(roleIds)) return true;

        return userRoleService.save(po.getId(), roleIds);
    }
}
