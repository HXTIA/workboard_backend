package run.hxtia.workbd.pojo.vo.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
@Api("技巧")
public class SkillVo {

    @ApiModelProperty("id")
    private Integer id;

    // eg：这里数据库查出来是 Data ，但是要返回的是时间戳
    @ApiModelProperty("创建时间")
    private Long createdTime;

    @ApiModelProperty("技能名称")
    private String name;

    @ApiModelProperty("技能等级")
    private Integer level;

}
