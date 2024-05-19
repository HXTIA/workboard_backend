package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教学班表(Course)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 教师ID
     */
    private Integer teacherId;

    /**
     * 所属学院ID
     */
    private Integer collegeId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

}

