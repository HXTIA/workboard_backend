package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户-作业表(UsersWorks)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("student_homeworks")
public class StudentHomework implements Serializable {
    private static final long serialVersionUID = 714852878467288369L;


    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 作业ID
     */
    private Long homeworkId;

    /**
     * 作业状态【0：未读未完成，1：已读未完成，2：已读已完成】
     */
    private Short status;

    /**
     * 是否置顶【0：不置顶，1：置顶】
     */
    private Short pin;

    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;

}

