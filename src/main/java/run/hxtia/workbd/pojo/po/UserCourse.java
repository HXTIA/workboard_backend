package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-教学班表(UsersCourses)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("users_courses")
public class UserCourse implements Serializable {
    private static final long serialVersionUID = 391944047937838883L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 班级ID
     */
    private Long courseId;

}

