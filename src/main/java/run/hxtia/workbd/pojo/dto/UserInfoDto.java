package run.hxtia.workbd.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.hxtia.workbd.pojo.vo.response.UserVo;

@Data
public class UserInfoDto {

    @ApiModelProperty("用户基本信息")
    private UserVo userVo;

    //TODO:用户班级信息

    //TODO：用户角色信息
}
