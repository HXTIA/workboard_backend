package run.hxtia.workbd.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static <T, R> List<R> list2List(Collection<T> list, Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    public static <T, R> List<R> list2List(Collection<T> list, Function<T, R> function, Predicate<R> predicate) {
        Stream<R> stream = list.stream().map(function);
        if (predicate != null) {
            stream = stream.filter(predicate);
        }
        return stream.collect(Collectors.toList());
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

    /**
     * 将 Collection<T> 转换成 Map<K, V>
     * @param list 待转换的集合
     * @param keyMapper 键的映射函数
     * @param valueMapper 值的映射函数
     * @param <T> 集合元素的类型
     * @param <K> Map 键的类型
     * @param <V> Map 值的类型
     * @return Map<K, V>
     */
    public static <T, K, V> Map<K, V> list2Map(Collection<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

}
