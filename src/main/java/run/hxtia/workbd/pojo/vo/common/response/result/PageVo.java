package run.hxtia.workbd.pojo.vo.common.response.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

// Service 分页结果
@Data
public class PageVo<T> {
    // 总数
    private Long count;

    // 总页数
    private Long pages;

    // 当前页
    private Long currentPage;

    // 每页显示的记录数
    private Long pageSize;

    // 数据
    private List<T> data;
}
