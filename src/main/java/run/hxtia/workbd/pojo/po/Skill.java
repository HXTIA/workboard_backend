package run.hxtia.workbd.pojo.po;

import lombok.Data;

import java.util.Date;

@Data
public class Skill {
    /**
     * 主键标识
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 技能名称
     */
    private String name;
    /**
     * 技能熟练等级
     */
    private Integer level;
}
