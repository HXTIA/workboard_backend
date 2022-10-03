package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.response.WorkVo;

@Data
@ApiModel("课程信息 + 作业信息 + 学期信息")
public class WorkInfoDto {

    @ApiModelProperty("作业信息")
    private WorkVo workVo;

    // TODO 课程信息

    // TODO 学期信息
}
