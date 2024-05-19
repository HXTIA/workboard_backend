package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.organization.response.OrganizationVo;

import java.util.List;

@Data
@ApiModel("C 端学生信息")
public class StudentInfoDto {

    @ApiModelProperty("学生基本信息")
    private StudentVo studentVo;

    @ApiModelProperty("用户组织信息：学院、年级、班级「若为 nil，则说明该生需要选择组织」")
    private OrganizationVo organizationVo;

    @ApiModelProperty("学生课程列表")
    private List<CourseVo> courseVos;

}
