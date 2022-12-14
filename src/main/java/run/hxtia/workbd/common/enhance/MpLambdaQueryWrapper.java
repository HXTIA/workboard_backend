package run.hxtia.workbd.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 加强 MybatisPlus 的 LambdaQueryWrapper
 * @param <T>：泛型的参数
 */
public class MpLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    /**
     * 加强 like
     * @param val：模糊查询的值
     * @param funcs：需要查询的字段
     * @return ：wrapper
     */
    @SafeVarargs
    public final MpLambdaQueryWrapper<T> like(Object val, SFunction<T, ?>... funcs) {

        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;

        /*
        拼接模糊查询条件
        1、nested是将其条件组合起来，最终包裹一个括号【去掉最后一个 .or】
        2、符合链式编程。方便拼接参数，返回值是父类，需强转为子类类型
         */
        return (MpLambdaQueryWrapper<T>) nested((w) -> {
            for (SFunction<T, ?> func : funcs) {
                w.like(func, str).or();
            }
        });
    }

    /**
     * 加强 between
     * @param val：开始日期 - 起始日期
     * @param funcs：字段
     * @return ：wrapper
     */
    @SafeVarargs
    public final MpLambdaQueryWrapper<T> between(Object[] val, SFunction<T, ?>... funcs) {
        if (val == null) return this;
        // 必须是开始日期和截止日期
        if (val.length != 2) return this;

        return (MpLambdaQueryWrapper<T>) nested((w) -> {
            for (SFunction<T, ?> func : funcs) {
                w.between(func, val[0], val[1]).or();
            }
        });
    }

    @Override
    public LambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        if (val == null) return this;
        return super.eq(column, val);
    }
}
