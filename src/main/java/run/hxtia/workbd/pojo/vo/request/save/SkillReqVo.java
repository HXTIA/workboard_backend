package run.hxtia.workbd.pojo.vo.request.save;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class SkillReqVo {

    @ApiModelProperty("id【大于0代表编辑，否则代表添加】")
    private Integer id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "技能名称", required = true)
    private String name;

    @ApiModelProperty(value = "掌握等级", required = true)
    @NotNull(message = "等级不能为空")
    private Integer level;

}
