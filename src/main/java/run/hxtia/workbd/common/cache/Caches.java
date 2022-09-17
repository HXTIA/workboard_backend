package run.hxtia.workbd.common.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

public class Caches {

    // 缓存构造器
    private static final CacheManager MNG;

    // 默认缓存
    private static final Cache<Object, Object> DEFAULT_CACHE;

    // Token 缓存
    private static final Cache<Object, Object> TOKEN_CACHE;

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

    }

    // 将数据放入缓存中 【key value 形式】
    public static void put(Object key, Object value) {
        if (key == null || value == null) return;
        DEFAULT_CACHE.put(key, value);
    }

    // 通过Key清除缓存
    public static void remove(Object key) {
        if (key == null) return;
        DEFAULT_CACHE.remove(key);
    }

    // 通过Key读取缓存
    public static <T> T get(Object key) {
        if (key == null) return null;
        return (T) DEFAULT_CACHE.get(key);
    }

    // 清除所有缓存
    public static void clear() {
        DEFAULT_CACHE.clear();
    }

    // Token缓存
    public static void putToken(Object key, Object value) {
        if (key == null || value == null) return;
        TOKEN_CACHE.put(key, value);
    }

    public static void removeToken(Object key) {
        if (key == null) return;
        TOKEN_CACHE.remove(key);
    }

    public static <T> T getToken(Object key) {
        if (key == null) return null;
        return (T) TOKEN_CACHE.get(key);
    }

    public static void clearToken() {
        TOKEN_CACHE.clear();
    }

}
