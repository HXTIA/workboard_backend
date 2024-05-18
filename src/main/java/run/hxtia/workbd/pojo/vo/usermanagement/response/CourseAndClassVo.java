package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;

import java.util.List;
import java.util.Map;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */

@Data
@ApiModel("被授权的课程、班级信息")
public class CourseAndClassVo {
    @ApiModelProperty("课程列表")
    private List<CourseVo> courseList;

    @ApiModelProperty("年级及其班级列表")
    private Map<String, List<ClassVo>> gradList;
}

