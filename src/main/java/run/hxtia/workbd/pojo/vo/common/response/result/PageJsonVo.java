package run.hxtia.workbd.pojo.vo.common.response.result;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页查询使用： 返回 数据 + 总条数
 * @param <T>：返回的数据：因为分页，查询的数据肯定是放List里，所以这里的泛型<T>相当与 List<T>
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PageJsonVo<T> extends DataJsonVo<List<T>> {

    @ApiModelProperty("总数")
    private Long count;

}
