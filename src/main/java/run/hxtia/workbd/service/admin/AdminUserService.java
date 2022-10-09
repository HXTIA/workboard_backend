package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.AdminUserInfoDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.page.AdminUserPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.*;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.response.AdminUserVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

@Transactional(readOnly = true)
public interface AdminUserService extends IService<AdminUsers> {


    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     */
    AdminLoginVo login(AdminLoginReqVo reqVo);

    /**
     * 超管注册
     * @param reqVo：注册的信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean register(AdminUserRegisterReqVo reqVo);

    /**
     * 添加用户 or 修改用户信息
     * @param reqVo ：用户信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(AdminUserReqVo reqVo);

    /**
     * 修改用户信息
     * @param reqVo ：用户信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(AdminUserEditReqVo reqVo);

    /**
     * 修改用户个人信息
     * @param reqVo ：用户信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(AdminUserInfoEditReqVo reqVo) throws Exception;

    /**
     * 修改密码
     * @param reqVo ：用户密码信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(AdminUserPasswordReqVo reqVo);

    /**
     * 通过Id获取用户信息【角色 + 组织 + 个人信息】
     * @param id：用户ID
     * @return ：用户信息
     */
    AdminUserInfoDto getAdminUserInfo(Integer id);

    /**
     *
     * @param userId:用户id
     * @return 用户信息
     */
    @Transactional(readOnly = false)
    AdminUserVo getAdminUserInfoById(Integer userId);

    /**
     *
     * @return 所有用户信息
     */
    @Transactional(readOnly = false)
    PageVo<AdminUserVo> getList(AdminUserPageReqVo pageReqVo);
}
