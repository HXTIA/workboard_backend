package run.hxtia.workbd.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 加强 MybatisPlus 的 QueryWrapper
 * @param <T>：泛型的参数
 */
public class MpQueryWrapper<T> extends QueryWrapper<T> {

    public MpQueryWrapper<T> like(Object val, String... columns) {
        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;

        // 与 MpLambdaQueryWrapper 同理
        return (MpQueryWrapper<T>) nested((w) -> {
            for (String column : columns) {
                w.like(column, str).or();
            }
        });
    }

}
