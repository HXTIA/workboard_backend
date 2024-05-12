package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("【编辑】完善用户信息")
public class StudentReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private String wechatId;

    @ApiModelProperty("用户姓名")
    private String nickname;

    @NotBlank
    @ApiModelProperty(value = "学号", required = true)
    private String studentId;

    @NotNull
    @ApiModelProperty(value = "学院 ID", required = true)
    private Integer collegeId;

    @NotNull
    @ApiModelProperty(value = "年级 ID", required = true)
    private Integer gradeId;

    @NotNull
    @ApiModelProperty(value = "班级 ID", required = true)
    private Integer classId;

}

