package run.hxtia.workbd.pojo.vo.common.response.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("分页结果")
@Data
public class PageVo<T> {


    @ApiModelProperty("总数")
    private Long count;

    @ApiModelProperty("总页数")
    private Long pages;

    @ApiModelProperty("数据")
    private List<T> data;
}
