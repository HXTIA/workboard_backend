package run.hxtia.workbd.pojo.vo.request.page.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 这是基础的分页的类
 */

@Data
public class PageReqVo {

    private static final int DEFAULT_SIZE = 10;

    @ApiModelProperty("页码")
    private Long page;

    @ApiModelProperty("每页的数量")
    private Long size;

    // 给一个默认值
    public PageReqVo() {
        this.page = 1L;
        this.size = 0L;
    }

    // 最小是第一页
    public Long getPage() {
        return Math.max(page, 1);
    }

    // 数量必须大于 0
    public  Long getSize() {
        return size < 1 ? DEFAULT_SIZE : size;
    }

}
