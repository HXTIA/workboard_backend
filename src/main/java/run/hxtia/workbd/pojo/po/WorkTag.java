package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 作业-标签表(WorksTags)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("works_tags")
public class WorkTag implements Serializable {
    private static final long serialVersionUID = 764923653489272375L;
    /**
     * 作业ID
     */
    private Long workId;
    /**
     * 标签ID
     */
    private Integer tagId;

}

