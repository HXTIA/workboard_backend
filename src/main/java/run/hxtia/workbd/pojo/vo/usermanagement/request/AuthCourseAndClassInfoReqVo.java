package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/15
 */
@Data
@ApiModel("【授权】B端授权模块，课程信息和班级信息请求体")
public class AuthCourseAndClassInfoReqVo {
    @ApiModelProperty("课程列表")
    private List<CourseVo> courseList;

    @ApiModelProperty("班级列表")
    private List<ClassVo> classIds;
}
