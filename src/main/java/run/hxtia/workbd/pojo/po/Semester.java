package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 学期表(Semesters)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("semesters")
public class Semester implements Serializable {
    private static final long serialVersionUID = 944335887169584027L;
    /**
     * 学期ID
     */
    private Integer id;
    /**
     * 学期描述
     */
    private String semesterDescribe;
    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 一学期的周数
     */
    private Short weeks;

}

