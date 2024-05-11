package run.hxtia.workbd.pojo.vo.organization.response;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生的组织信息响应对象
 */
@Data
@ApiModel("学生的组织详细信息")
public class OrganizationVo {
    @ApiModelProperty("学院信息")
    private CollegeVo college;

    @ApiModelProperty("年级信息")
    private GradeVo grade;

    @ApiModelProperty("班级信息")
    private ClassVo classVo;
}
