package run.hxtia.workbd.common.redis;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 配置类
 * Q：为什么不将配置文件放入 config包中？
 * A：因为与 redis关联较为密切，所以直接放入redis 的包中，并且以为后缀Config
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        System.out.println("\nRedisConfig__________redisCacheConfiguration\n");
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer =
            new FastJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(
            RedisSerializationContext.SerializationPair.
                fromSerializer(fastJsonRedisSerializer)
        ).entryTtl(Duration.ofHours(2));
        return configuration;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        System.out.println("\nRedisConfig__________redisTemplate\n");

        // 序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // value采用fastjson序列化
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key采用 StringRedisSerializer 序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义缓存 key 生成策略 【默认使用该策略】
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        System.out.println("\nRedisConfig__________keyGenerator\n");

        return ((target, method, params) -> {
            Map<String, Object> map = new HashMap<>(8);
            Class<?> targetCls = target.getClass();
            // 类地址
            map.put("class", targetCls.toGenericString());
            // 方法名称
            map.put("methodName", method.getName());
            // 包名称
            map.put("package", targetCls.getPackage());
            // 参数列表
            for (int i = 0; i < params.length; i++) {
                map.put(String.valueOf(i), params[i]);
            }
            // 算出一个 hash值，作为key
            return DigestUtils.sha256(JSON.toJSONString(map));
        });
    }
}
