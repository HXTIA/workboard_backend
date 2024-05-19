package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassIdReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */

@Data
@ApiModel("授权码生成者+授权课程列表+授权班级列表")
public class CodesInfoDto {
    // 发布者id
    private Long publisherId;

    // 课程+班级信息
    private AuthCourseAndClassIdReqVo courseAndClassVo;
}
