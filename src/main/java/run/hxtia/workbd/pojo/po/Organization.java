package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 组织表(Organization)实体类
 *
 * @author ZhiYan
 * @since 2022-09-23 01:28:33
 */
@Data
@TableName("organization")
public class Organization implements Serializable {
    private static final long serialVersionUID = 548924526732632300L;
    /**
     * 组织ID
     */
    private Short id;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织背景【旗帜】
     */
    private String background;
    /**
     * 创建日期
     */
    private Date createdAt;

}

