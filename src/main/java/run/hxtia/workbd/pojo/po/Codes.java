package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */

@Data
@TableName("codes")
public class Codes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动递增的主键标识符
     */
    private Integer id;

    /**
     * 唯一的代码字符串，用于标识授权码
     */
    private String code;

    /**
     * 生成授权码的用户ID
     */
    private Integer publishId;

    /**
     * 关联的课程ID
     */
    private String courseId;

    /**
     * 关联的班级ID
     */
    private String classId;

    /**
     * 代码的状态（1表示未使用，2表示已使用，3表示已吊销）
     */
    private Short status;

    /**
     * 记录创建时间
     */
    private Date createdAt;

    /**
     * 记录最后更新时间
     */
    private Date updatedAt;
}
