package run.hxtia.workbd.pojo.po;


import lombok.Data;

import java.util.Date;


@Data
public class User {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 用户最后一次登录时间
     */
    private Date loginTime;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户邮箱
     */
    private String password;
    /**
     * 用户用户昵称
     */
    private String nickname;
    /**
     * 用户状态【1：可用；0：禁用】
     */
    private Short state;
}
