package run.hxtia.workbd.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户-教学班表(UsersCourses)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
public class UsersCourses implements Serializable {
    private static final long serialVersionUID = 391944047937838883L;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 班级ID
     */
    private Long courseId;

}

