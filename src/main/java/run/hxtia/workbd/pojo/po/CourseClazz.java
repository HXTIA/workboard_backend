package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 教学班-行政班表(CoursesClazz)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("courses_clazz")
public class CourseClazz implements Serializable {
    private static final long serialVersionUID = 967203935724250859L;
    /**
     * 课程ID
     */
    private Integer id;
    /**
     * 班级ID
     */
    private Integer classId;
    /**
     * 组织ID
     */
    private Long orgId;

}

