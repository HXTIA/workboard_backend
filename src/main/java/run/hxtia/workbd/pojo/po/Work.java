package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 作业表(Work)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("work")
public class Work implements Serializable {
    private static final long serialVersionUID = -10748310609330681L;
    /**
     * 作业ID
     */
    private Long id;
    /**
     * 课程ID
     */
    private Integer courseId;
    /**
     * 学期ID
     */
    private Integer semesterId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作业标题
     */
    private String title;
    /**
     * 作业内容
     */
    private String detail;
    /**
     * 截止日期
     */
    private Date dateline;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 是否展示【1：展示，0：不展示】
     */
    private Short show;

}

