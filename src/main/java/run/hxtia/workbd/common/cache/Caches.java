package run.hxtia.workbd.common.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.util.StringUtils;

import java.net.URL;

public class Caches {

    /**
     * 缓存构造器
     */
    private static final CacheManager MNG;

    /**
     * 默认缓存
     */
    private static final Cache<Object, Object> DEFAULT_CACHE;
    /**
     * Token 缓存
     */
    private static final Cache<Object, Object> TOKEN_CACHE;
    /**
     * code 缓存
     */
    private static final Cache<Object, Object> CODE_CACHE;

    static {
        // 初始化缓存管理器
        URL url = Caches.class.getClassLoader().getResource("ehcache.xml");
        assert url != null;
        Configuration cfg = new XmlConfiguration(url);

        MNG = CacheManagerBuilder.newCacheManager(cfg);
        MNG.init();

        // 缓存对象
        DEFAULT_CACHE = MNG.getCache("default", Object.class, Object.class);
        TOKEN_CACHE = MNG.getCache("token", Object.class, Object.class);
        CODE_CACHE = MNG.getCache("code", Object.class, Object.class);

    }

    /**
     * 将数据放入缓存中 【key value 形式】
     * @param key：键
     * @param value：值
     */
    public static void put(Object key, Object value) {
        if (key == null || value == null) return;
        DEFAULT_CACHE.put(key, value);
    }

    /**
     * 通过Key清除缓存
     * @param key：键
     */
    public static void remove(Object key) {
        if (key == null) return;
        DEFAULT_CACHE.remove(key);
    }

    /**
     * 通过Key读取缓存
     * @param key：键
     * @param <T>：返回的参数类型
     * @return ：具体缓存的数据
     */
    public static <T> T get(Object key) {
        if (key == null) return null;
        return (T) DEFAULT_CACHE.get(key);
    }

    /**
     * 清除所有缓存
     */
    public static void clear() {
        DEFAULT_CACHE.clear();
    }

    /**
     * Token缓存
     * @param key：键
     * @param value：值
     */
    public static void putToken(Object key, Object value) {
        if (key == null || value == null) return;
        TOKEN_CACHE.put(key, value);
    }

    /**
     * 通过key清除Token缓存
     * @param key：键
     */
    public static void removeToken(Object key) {
        if (key == null) return;
        TOKEN_CACHE.remove(key);
    }

    /**
     * 获取Token缓存
     * @param key：键
     * @param <T>：缓存的数据类型
     * @return ：具体内容
     */
    public static <T> T getToken(Object key) {
        if (key == null) return null;
        return (T) TOKEN_CACHE.get(key);
    }

    /**
     * 清除所有的 Token缓存
     */
    public static void clearToken() {
        TOKEN_CACHE.clear();
    }

    /**
     * 将数据放入缓存中 【key value 形式】
     * @param key：键
     * @param value：值
     */
    public static void putCode(Object key, Object value) {
        if (key == null || value == null) return;
        CODE_CACHE.put(key, value);
    }

    /**
     * 通过Key读取缓存
     * @param key：键
     * @param <T>：返回的参数类型
     * @return ：具体缓存的数据
     */
    public static <T> T getCode(Object key) {
        if (key == null) return null;
        return (T) CODE_CACHE.get(key);
    }

    /**
     * 验证是否已经存在此验证码
     * @param key：键
     * @return ：是否存在
     */
    public static boolean isExistCode(Object key) {
        return isExistCode("", key);
    }

    /**
     * 验证是否已经存在此验证码
     * @param prefix：前缀
     * @param key：键
     * @return ：是否存在
     */
    public static boolean isExistCode(String prefix, Object key) {
        if (key == null) return false;
        return getCode(prefix + key) != null;
    }

    /**
     * 检查验证是否正确
     * @param key：键
     * @param awayCode：待验证的码
     * @return ：是否相等
     */
    public static boolean checkCode(Object key, String awayCode) {
        return checkCode("", key, awayCode);
    }

    /**
     * 检查验证是否正确
     * @param prefix：前缀
     * @param key：键
     * @param awayCode：待验证的码
     * @return ：是否相等
     */
    public static boolean checkCode(String prefix, Object key, String awayCode) {
        if (key == null) return false;
        String code = getCode(prefix + key);
        if (!StringUtils.hasLength(code)) return false;
        return code.equals(awayCode);
    }

}
