package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * B端用户表(AdminUsers)实体类
 *
 * @author ZhiYan
 * @since 2022-09-23 01:28:34
 */
@Data
@TableName("admin_users")
public class AdminUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    /**
     * 所属学院ID
     */
    private Integer collegeId;

    /**
     * 密码的盐值
     */
    private String salt;

    /**
     * 用户状态【1：可用 0：禁用】
     */
    private Short status;

    /**
     * 姓名
     */
    private String nickname;

}

