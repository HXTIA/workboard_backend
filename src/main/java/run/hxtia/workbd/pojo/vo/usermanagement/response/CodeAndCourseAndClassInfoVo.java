package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */
@Data
@ApiModel("授权码、课程、班级信息")
public class CodeAndCourseAndClassInfoVo {

    @ApiModelProperty("授权码")
    private String code;

    @ApiModelProperty("授权课程名称")
    private String courseName;

    @ApiModelProperty("授权班级名称")
    private String className;

    @ApiModelProperty("授权班级所在年级的名字")
    private String gradeName;

    @ApiModelProperty("授权所在学院名称")
    private String collegeName;

    @ApiModelProperty("授权码状态（1表示未使用，2表示已使用，3表示已吊销）")
    private Integer status;

    @ApiModelProperty("授权码创建日期")
    private String createdAt;
}
