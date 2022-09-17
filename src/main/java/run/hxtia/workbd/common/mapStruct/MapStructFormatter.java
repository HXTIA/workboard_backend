package run.hxtia.workbd.common.mapStruct;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

/**
 * 自定义转换
 */
public class MapStructFormatter {

    // 必须使用的规定格式：自定义转换类型【日期 -> 时间戳】
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Date2Millis {}

    /**
     * 将 Date类型转换成 毫秒数
     * @param date：日期
     * @return ：时间戳
     */
    @Date2Millis
    public static Long date2millis(Date date) {
        if (date == null) return null;
        return date.getTime();
    }

    // 必须使用的规定格式：自定义转换类型【时间戳 -> 日期】
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Mills2Date {}

    /**
     * 将 毫秒数类型转换成 Date
     * @param mills：时间戳
     * @return ：日期
     */
    @Mills2Date
    public static Date millis2date(Long mills) {
        if (mills == null) return null;
        return new Date(mills);
    }
}
