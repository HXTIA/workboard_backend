package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * C端用户表(Users)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("students")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 微信ID
     */
    private String wechatId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 电话号码
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
     * 学院ID
     */
    private Integer collegeId;

    /**
     * 年级ID
     */
    private Integer gradeId;

    /**
     * 班级ID
     */
    private Integer classId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 是否是 C 端管理员「1：是，0：不是」
     */
    private Short author;
}

