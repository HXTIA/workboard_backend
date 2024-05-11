package run.hxtia.workbd.pojo.vo.usermanagement.response;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("角色信息")
public class RoleVo {
    // 主要是想要在Role的基础上，加上Swagger文档的注释

    @ApiModelProperty("角色ID")
    private Short id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色简介")
    private String intro;

}

