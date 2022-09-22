package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户表(Users)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("users")
public class User implements Serializable {
    private static final long serialVersionUID = 140800167149720290L;
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户微信openID
     */
    private String openid;
    /**
     * 用户头像地址
     */
    private String avatarUrl;
    /**
     * 用户姓名
     */
    private String nickname;
    /**
     * 学号
     */
    private String studentId;
    /**
     * 班级ID
     */
    private Long classId;

}

