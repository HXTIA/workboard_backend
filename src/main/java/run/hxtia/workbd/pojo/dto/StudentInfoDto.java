package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.response.StudentVo;

@Data
@ApiModel("C端用户信息")
public class StudentInfoDto {

    @ApiModelProperty("用户基本信息")
    private StudentVo studentVo;

    // TODO：用户组织信息（学院、班级、年级）

    // TODO：课程信息

    // TODO：是否拥有组织
}
