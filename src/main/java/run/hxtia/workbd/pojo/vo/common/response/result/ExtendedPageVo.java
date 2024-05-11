package run.hxtia.workbd.pojo.vo.common.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

/**
 * @author Xiaojin
 * @date 2024/5/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExtendedPageVo<T> extends PageVo<T> {
    @ApiModelProperty("当前页")
    private Long currentPage;

    @ApiModelProperty("每页显示的记录数")
    private Long pageSize;

    // 这里可以添加更多需要的字段
}
