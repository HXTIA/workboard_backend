package run.hxtia.workbd.pojo.vo.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.request.page.base.PageReqVo;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClassPageReqVo extends PageReqVo {

    @ApiModelProperty("年级ID")
    private Integer gradeId;

}
