package run.hxtia.workbd.common.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类
 */
public class Streams {

    /**
     * 将 List<T> -> List<R>
     * @param list ：待转换的集合
     * @param function ：转换的方式
     * @param <T> ：待转换的类型
     * @param <R> ：目标的类型
     * @return ：List<R>
     */
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    /**
     * 过滤集合
     * @param list：待过滤集合
     * @param predicate：过滤的条件
     * @param <T>：类型
     * @return ：过滤后的集合
     */
    public static <T> List<T> filter(Collection<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

}
