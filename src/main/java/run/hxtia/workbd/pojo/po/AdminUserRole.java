package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户-角色表(UsersRoles)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("admin_users_roles")
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserRole implements Serializable {
    private static final long serialVersionUID = -70924790322874581L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Short roleId;

}

