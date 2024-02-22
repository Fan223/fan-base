package fan.redis;

import fan.core.collection.ArrayUtil;
import fan.core.collection.CollectionUtil;
import fan.core.text.StringUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis utility class
 *
 * @author Fan
 * @since 2024/2/21 15:53
 */
@Component
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    private void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        staticSetRedisTemplate(redisTemplate);
    }

    private static synchronized void staticSetRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * Set expiration time
     *
     * @param key    Key
     * @param expire Expiration time, in seconds
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:45
     */
    public static boolean expire(String key, long expire) {
        if (expire < 0) {
            logger.error("Expiration time less than 0");
            return false;
        }
        return Boolean.TRUE.equals(redisTemplate.expire(key, expire, TimeUnit.SECONDS));
    }

    /**
     * Delete multiple cache
     *
     * @param keys Keys
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:49
     */
    public static boolean del(String... keys) {
        if (ArrayUtil.isEmpty(keys)) {
            logger.error("Key is null");
            return false;
        }
        redisTemplate.delete(Arrays.asList(keys));
        return true;
    }

    /**
     * Batch delete cache
     *
     * @param keys Keys
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:50
     */
    public static boolean del(List<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            logger.error("Keys are null");
            return false;
        }
        redisTemplate.delete(keys);
        return true;
    }

    /**
     * Check whether key exists
     *
     * @param key Key
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:52
     */
    public static boolean hasKey(String key) {
        return StringUtil.isNotBlank(key) && Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Set cache for {@link String} type
     *
     * @param key   Key
     * @param value Value
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:54
     */
    public static boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * Set cache for {@link String} type with expiration time
     *
     * @param key    Key
     * @param value  Value
     * @param expire Expiration time, in seconds
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:56
     */
    public static boolean set(String key, Object value, long expire) {
        if (expire < 0) {
            logger.error("Expiration time less than 0");
            return false;
        }
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        return true;
    }

    /**
     * Retrieve cache for {@link String} type
     *
     * @param key Key
     * @return {@link Object}
     * @author Fan
     * @since 2024/2/21 16:57
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Set cache for Hash type
     *
     * @param key     Key
     * @param hashKey HashKey
     * @param value   Value
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:58
     */
    public static boolean hashSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return true;
    }

    /**
     * Set cache for Hash type with expiration time
     *
     * @param key     Key
     * @param hashKey HashKey
     * @param value   Value
     * @param expire  Expiration time, in seconds
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 16:59
     */
    public static boolean hashSet(String key, String hashKey, Object value, long expire) {
        hashSet(key, hashKey, value);
        expire(key, expire);
        return true;
    }

    /**
     * Retrieve cache for Hash type
     *
     * @param key     Key
     * @param hashKey HashKey
     * @return {@link Object}
     * @author Fan
     * @since 2024/2/21 17:00
     */
    public static Object hashGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * Delete multiple cache for Hash type
     *
     * @param key     Key
     * @param hashKey HashKey
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/21 17:01
     */
    public static boolean hashDel(String key, Object... hashKey) {
        if (StringUtil.isBlank(key) || ArrayUtil.isEmpty(hashKey)) {
            logger.error("Key or HashKey is null");
            return false;
        }
        redisTemplate.opsForHash().delete(key, hashKey);
        return true;
    }
}