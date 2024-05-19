package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("homeworks")
public class Homework implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 作业ID
     */
    @TableId
    private Long id;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业描述
     */
    private String description;

    /**
     * 图片链接
     */
    private String pictureLinks;

    /**
     * 截止日期
     */
    private Date deadline;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 发布者ID
     */
    private String publisherId;

    /**
     * 发布平台
     */
    private String publishPlatform;

    /**
     * 状态
     */
    private Short status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

}

