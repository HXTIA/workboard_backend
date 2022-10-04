package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Md5s;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.mapper.AdminUserMapper;
import run.hxtia.workbd.pojo.dto.AdminUserInfoDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Organization;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.*;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.admin.AdminUserService;
import run.hxtia.workbd.service.admin.OrganizationService;
import run.hxtia.workbd.service.admin.AdminUserRoleService;
import run.hxtia.workbd.service.admin.RoleService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl
    extends ServiceImpl<AdminUserMapper, AdminUsers> implements AdminUserService {

    private final Redises redises;
    private final OrganizationService orgService;
    private final AdminUserRoleService adminUserRoleService;
    private final RoleService roleService;

    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     */
    @Override
    public AdminLoginVo login(AdminLoginReqVo reqVo) {

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
        if (userPo.getStatus() == Constants.Status.UNABLE) {
            return JsonVos.raise(CodeMsg.USER_LOCKED);
        }

        // TODO：1、查询角色信息 2、查询角色资源信息 3、缓存到 Redis

        // 生成Token
        String token = Strings.getUUID();

        // 将对象其放入缓存中
        redises.set(Constants.Web.ADMIN_PREFIX, token, userPo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        // 将用户Token 放入 缓存
        redises.set(Constants.Users.USER_ID, String.valueOf(userPo.getId()), Constants.Web.HEADER_TOKEN + token);

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
        if (!orgService.saveDefaultRegisterOrg(defaultOrg)) {
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
        if (!(baseMapper.insert(po) > 0)) return JsonVos.raise(CodeMsg.OPERATE_OK);

        // 给组织发起者，添加上超级管理员的角色
        LambdaQueryWrapper<Role> roleMapper = new LambdaQueryWrapper<>();
        roleMapper.eq(Role::getName, Constants.Users.DEFAULT_ROLE);
        Short roleId = roleService.getOne(roleMapper).getId();

        if (!adminUserRoleService.save(po.getId(), String.valueOf(roleId))) {
            return JsonVos.raise(CodeMsg.ADD_ROLE_ERROR);
        }

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

        return adminUserRoleService.save(po.getId(), roleIds);
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
        redises.delByUserId(id);

        // 清除该用户所有的角色
        adminUserRoleService.removeByUserId(id);

        // 保存 or 编辑用户信息
        if (!updateById(po)) return false;

        String roleIds = reqVo.getRoleIds();
        // 说明没有分配角色信息，那么直接返回成功
        if (!StringUtils.hasLength(roleIds)) return true;

        return adminUserRoleService.save(po.getId(), roleIds);
    }

    /**
     * 修改用户个人信息
     * @param reqVo ：用户信息【带头像】
     * @return ：是否成功
     */
    @Override
    public boolean update(AdminUserInfoEditReqVo reqVo) throws Exception {

        AdminUsers po = MapStructs.INSTANCE.reqVo2po(reqVo);

        return Uploads.uploadOneWithPo(po,
            new UploadReqParam(reqVo.getAvatarUrl(),
                reqVo.getAvatarFile()), baseMapper,
            AdminUsers::setAvatarUrl);
    }

    /**
     * 修改密码
     * @param reqVo ：用户密码信息
     * @return ：是否成功
     */
    @Override
    public boolean update(AdminUserPasswordReqVo reqVo) {
        AdminUsers adminUsers = baseMapper.selectById(reqVo.getId());
        if (adminUsers == null) return false;

        String oldPsd = adminUsers.getPassword();
        String oldSalt = adminUsers.getSalt();

        // 验证旧密码是否正确
        if (!Md5s.verify(reqVo.getOldPassword(), oldSalt, oldPsd)) {
            return JsonVos.raise(CodeMsg.WRONG_OLD_PASSWORD);
        }

        // 验证新密码与旧密码是否重复
        if (Md5s.verify(reqVo.getNewPassword(), oldSalt, oldPsd)) {
            return JsonVos.raise(CodeMsg.WRONG_NEW_PASSWORD_REPEAT);
        }

        // 设置新密码
        String newSalt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String newPsd = Md5s.md5(reqVo.getNewPassword(), newSalt);

        adminUsers.setPassword(newPsd);
        adminUsers.setSalt(newSalt);

        return updateById(adminUsers);
    }

    /**
     * 通过Id获取用户信息【角色 + 组织 + 个人信息】
     * @param userId：用户ID
     * @return ：用户信息
     */
    @Override
    public AdminUserInfoDto getAdminUserInfo(Integer userId) {
        if (userId == null || userId <= 0) return null;

        // TODO:获取用户信息【待队友实现】

        // 获取用户角色
        List<Role> roles = roleService.listByIds(adminUserRoleService.listRoleIds(userId));

        // TODO: 根据上面获取的组织ID，获取组织信息

        AdminUserInfoDto dto = new AdminUserInfoDto();
        dto.setRoles(roles);

        return dto;
    }
}
