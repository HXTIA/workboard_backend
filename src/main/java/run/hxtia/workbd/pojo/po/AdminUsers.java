package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * B端用户表(AdminUsers)实体类
 *
 * @author ZhiYan
 * @since 2022-09-23 01:28:34
 */
@Data
@TableName("admin_users")
public class AdminUsers implements Serializable {
    private static final long serialVersionUID = 166963919420133360L;
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     * 1. 超管暂时使用邮箱验证
     * 2、超管创建的管理员是自定义的账号格式（如：tju2021）
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码的盐值
     */
    private String salt;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 组织ID
     */
    private Short orgId;
    /**
     * 用户状态【1：可用 0：禁用】
     */
    private Short status;

}

