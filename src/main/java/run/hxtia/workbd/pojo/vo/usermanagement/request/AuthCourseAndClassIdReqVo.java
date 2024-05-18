package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/15
 */
@Data
@ApiModel("【授权】B端授权模块，课程id列表和班级id列表请求体")
public class AuthCourseAndClassIdReqVo {


    @ApiModelProperty("课程ID列表")
    private String courseIds;

    @ApiModelProperty("班级ID列表")
    private String classIds;
}
