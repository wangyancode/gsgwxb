package com.jsdc.gsgwxb.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisLock
 * Description:
 * date: 2024/4/29 19:39
 *
 * @author bn
 */
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final RedisScript<Long> unlockScript = new DefaultRedisScript<>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else " +
                    "return 0 " +
                    "end",
            Long.class
    );

    public boolean acquireLock(String key, String requestId, long expire) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, requestId, expire,TimeUnit.MILLISECONDS);
        return result != null && result;
    }

    public boolean releaseLock(String key, String requestId) {
        Long result = redisTemplate.execute(unlockScript, Collections.singletonList(key), requestId);
        return result != null && result > 0;
    }
}
