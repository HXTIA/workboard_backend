package run.hxtia.workbd.pojo.vo.common.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 这是带关键字的分页查询请求，若没其他需求，继承这个就行。
 * 若还有其他参数，在继承此基础上增加新的参数
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class KeywordPageReqVo extends PageReqVo {

    @ApiModelProperty("查询的关键字")
    private String keyword;
}
