package run.hxtia.workbd.pojo.vo.notificationwork.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("学生信息")
public class StudentVo {

    @ApiModelProperty("学生 ID")
    private String wechatId;

    @ApiModelProperty("头像地址")
    private String avatarUrl;

    @ApiModelProperty("学生姓名")
    private String nickname;

    @ApiModelProperty("学号")
    private String studentId;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("学院 ID")
    private Integer collegeId;

    @ApiModelProperty("年级 ID")
    private Integer gradeId;

    @ApiModelProperty("班级 ID")
    private Integer classId;

    @ApiModelProperty("是否是 C 端管理员")
    private Short author;
}
