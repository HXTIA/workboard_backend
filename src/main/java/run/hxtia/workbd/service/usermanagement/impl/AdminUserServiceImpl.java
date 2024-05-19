package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.*;
import run.hxtia.workbd.mapper.AdminUserMapper;
import run.hxtia.workbd.pojo.dto.AdminUserInfoDto;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.po.Resource;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.AdminUserPageReqVo;
import run.hxtia.workbd.pojo.vo.common.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AdminUserVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.*;
import run.hxtia.workbd.service.organization.CollegeService;
import run.hxtia.workbd.service.usermanagement.AdminUserRoleService;
import run.hxtia.workbd.service.usermanagement.AdminUserService;
import run.hxtia.workbd.service.usermanagement.ResourceService;
import run.hxtia.workbd.service.usermanagement.RoleService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl
    extends ServiceImpl<AdminUserMapper, AdminUsers> implements AdminUserService {

    private final Redises redises;
    private final AdminUserRoleService adminUserRoleService;
    private final RoleService roleService;
    private final ResourceService resourceService;
    private final CollegeService collegeService;

    /**
     * 用户登录
     *
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
            return JsonVos.raise(CodeMsg.WRONG_USERNAME_NOT_EXIST);
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

        // 1、查询角色信息 2、查询角色资源信息 3、缓存到 Redis
        AdminUserPermissionDto dto = new AdminUserPermissionDto();
        dto.setUsers(userPo);

        // 查询角色信息
        List<Role> roles = roleService.listByUserId(userPo.getId());
        if (!CollectionUtils.isEmpty(roles)) {
            dto.setRoles(roles);

            // 查询资源信息
            List<Short> roleIds = Streams.list2List(roles, Role::getId);
            List<Resource> resources = resourceService.listByRoleIds(roleIds);
            dto.setResources(resources);
        }

        // 生成Token
        String token = Strings.getUUID();

        // 将对象其放入缓存中
        redises.set(Constants.Web.ADMIN_PREFIX, token, dto, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        // 将用户Token 放入 缓存
        redises.set(Constants.Users.USER_ID, String.valueOf(userPo.getId()), Constants.Web.ADMIN_PREFIX + token);

        // 将 po -> vo
        AdminLoginVo vo = MapStructs.INSTANCE.po2loginVo(userPo);
        vo.setToken(token);

        return vo;
    }

    /**
     * 超管注册
     *
     * @param reqVo：注册的信息
     * @return ：是否成功
     */
    @Override
    public boolean register(AdminUserRegisterReqVo reqVo) {

        // 检查验证码并且返回 po
        AdminUsers po = checkCodeAndPo(reqVo.getEmail(), reqVo.getCode());

        // 验证用户是否存在
        if (po != null) return JsonVos.raise(CodeMsg.EXIST_USERS);

        // 注册学院
        College defaultClg = new College();
        if (!collegeService.saveDefaultRegisterClg(defaultClg)) {
            return JsonVos.raise(CodeMsg.REGISTER_ERROR);
        }

        // 密码加盐
        String salt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String psdWithMd5 = Md5s.md5(reqVo.getPassword(), salt);
        // 这里比较特殊，都得自己手动设置值
        po = MapStructs.INSTANCE.reqVo2po(reqVo);
        po.setSalt(salt);
        po.setPassword(psdWithMd5);
        po.setCollegeId(defaultClg.getId());

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
     *
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
        Integer colId = baseMapper.selectById(reqVo.getOperatorId()).getCollegeId();

        // 密码加盐
        String salt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String psdWithMd5 = Md5s.md5(reqVo.getPassword(), salt);
        po.setCollegeId(colId);
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
     *
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
     *
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
     *
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

        return updatePwd(adminUsers, reqVo.getNewPassword());
    }

    /**
     * 通过Id获取用户信息【角色 + 组织 + 个人信息】
     *
     * @param userId：用户ID
     * @return ：用户信息
     */
    @Override
    public AdminUserInfoDto getAdminUserInfo(Long userId) {
        if (userId == null || userId <= 0) return null;

        // 获取用户信息
        AdminUsers adminUsers = baseMapper.selectById(userId);
        AdminUserVo adminUserVo = MapStructs.INSTANCE.po2adminUserVo(adminUsers);
        // 获取用户角色
        List<Role> roles = roleService.listByIds(adminUserRoleService.listRoleIds(userId));
        // TODO：根据上面获取的组织ID，获取组织信息
//        Organization organization = orgService.getById(adminUsers.getOrgId());
//        OrganizationVo organizationVo = MapStructs.INSTANCE.po2vo(organization);
        AdminUserInfoDto dto = new AdminUserInfoDto();
        dto.setRoles(roles);
        dto.setUserVo(adminUserVo);

        // dto.setOrgVo(organizationVo);
        return dto;
    }

    /**
     *
     * @param userId:用户id
     * @return vo:用户信息
     */
    @Override
    public AdminUserVo getAdminUserInfoById(Long userId) {
        //根据id查询用户信息
        AdminUsers adminUser = baseMapper.selectById(userId);
        //用户不存在
        if (adminUser == null) {
            return JsonVos.raise(CodeMsg.WRONG_USERNAME_NOT_EXIST);
        }
        //将po转换为vo
        AdminUserVo adminUserVo = MapStructs.INSTANCE.po2adminUserVo(adminUser);
        return adminUserVo;
    }

    @Override
    public PageVo<AdminUserVo> getList(AdminUserPageReqVo pageReqVo) {

        MpLambdaQueryWrapper<AdminUsers> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), AdminUsers::getUsername, AdminUsers::getNickname);

        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo(MapStructs.INSTANCE::po2adminUserVo);

    }
    /**
     * 忘记密码，并且修改密码
     * @param reqVo：请求参数
     * @return ：是否成功
     */
    @Override
    public boolean forgotPwd(AdminUserForgotReqVo reqVo) {
        // 检查验证码并且返回 po
        AdminUsers po = checkCodeAndPo(reqVo.getEmail(), reqVo.getCode());
        if (po == null) return JsonVos.raise(CodeMsg.WRONG_USERNAME_NOT_EXIST);

        // 1、验证新密码 2、保存新密码
        return updatePwd(po, reqVo.getNewPassword());
    }

    /**
     * 修改组织成员密码【替组织成员找回密码】
     * @param reqVo：请求参数
     * @return ：是否成功
     */
    @Override
    public boolean update(AdminUserMemberPwdReqVo reqVo) {
        AdminUsers po = baseMapper.selectById(reqVo.getId());
        if (po == null) return JsonVos.raise(CodeMsg.WRONG_USERNAME_NOT_EXIST);
        // 1、验证新密码 2、保存新密码
        return updatePwd(po, reqVo.getNewPassword());
    }

    /**
     * 验证是否与旧密码重复，并且修改密码
     * @param po：修改的用户
     * @param newPassword：新密码
     * @return ：是否成功
     */
    private boolean updatePwd(AdminUsers po, String newPassword) {
        // 验证新密码与旧密码是否重复
        if (Md5s.verify(newPassword, po.getSalt(), po.getPassword())) {
            return JsonVos.raise(CodeMsg.WRONG_NEW_PASSWORD_REPEAT);
        }

        // 设置新密码
        String newSalt = Strings.getUUID(Md5s.DEFAULT_SALT_LEN);
        String newPsd = Md5s.md5(newPassword, newSalt);

        po.setPassword(newPsd);
        po.setSalt(newSalt);
        boolean res = updateById(po);
        // 保存成功，将用户在redis中的缓存踢下线
        if (res) redises.delByUserId(po.getId());
        return res;
    }

    /**
     * 检查验证码和并且返回用户
     * @param email：邮箱
     * @param code：验证码
     * @return ：对应的用户
     */
    private AdminUsers checkCodeAndPo (String email, String code) {
        // 验证验证码
        boolean checkCode = Caches.checkCode(Constants.VerificationCode.EMAIL_CODE_PREFIX, email, code);
        if (!checkCode) return JsonVos.raise(CodeMsg.WRONG_CAPTCHA);

        LambdaQueryWrapper<AdminUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUsers::getEmail, email);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 获取用户分页信息
     * @param pageReqVo 分页信息
     * @param token : 请求的token
     * @return 返回用户基本信息
     */
    @Override
    public PageVo<AdminUserVo> getList(AdminUserPageReqVo pageReqVo, String token) {

        MpLambdaQueryWrapper<AdminUsers> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), AdminUsers::getUsername, AdminUsers::getNickname).
            eq(AdminUsers::getCollegeId, Redises.getClgIdByToken(token));

        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo(MapStructs.INSTANCE::po2adminUserVo);

    }
}
