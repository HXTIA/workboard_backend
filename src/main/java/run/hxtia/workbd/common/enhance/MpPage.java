package run.hxtia.workbd.common.enhance;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

import java.util.List;
import java.util.function.Function;

/**
 * 加强版MybatisPlus分页查询
 * @param <T>
 */
public class MpPage<T> extends Page<T> {

    private final PageReqVo reqVo;

    public MpPage(PageReqVo reqVo) {
        // 分页需要的参数，传递给 MybatisPlus 的 Page
        super(reqVo.getPage(), reqVo.getSize());
        this.reqVo = reqVo;
    }

    /**
     * 构建分页查询的返回值
     * @param function：函数式编程，传入一个函数，【 T -> R 】如何转换
     * @param <R>：最终返回的类型
     * @return ：分页结果
     */
    public <R> PageVo<R> buildVo(Function<T, R> function) {
        return commonBuildVo(Streams.list2List(getRecords(), function));
    }

    /**
     * 查询出来是什么类型，返回什么类型
     * @return ：没有将 po -> vo 的分页结果。【用的很少】
     */
    public PageVo<T> buildVo() {
        return commonBuildVo(getRecords());
    }

    /**
     * 构建结果
     * @param data：分页数据
     * @param <R> ：返回的类型
     * @return ：分页结果【分页参数 + 查询结果】
     */
    private <R> PageVo<R> commonBuildVo(List<R> data) {
        // 给最终使用的查询参数，
        reqVo.setPage(getCurrent());
        reqVo.setSize(getSize());

        // 构建返回结果
        PageVo<R> pageVo = new PageVo<>();
        pageVo.setCount(getTotal());
        pageVo.setPages(getPages());
        pageVo.setCurrentPage(getCurrent());
        pageVo.setPageSize(getSize());
        pageVo.setData(data);

        return pageVo;
    }

}
