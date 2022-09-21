package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 教学班表(Course)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("course")
public class Course implements Serializable {
    private static final long serialVersionUID = -51971139414152132L;
    /**
     * 课程ID
     */
    private Integer id;
    /**
     * 学期ID
     */
    private Integer semesterId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程类型【0：选修，1：必修】
     */
    private Short courseType;

}

