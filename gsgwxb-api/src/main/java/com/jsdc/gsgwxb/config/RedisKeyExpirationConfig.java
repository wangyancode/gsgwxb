package com.jsdc.gsgwxb.config;

import com.jsdc.gsgwxb.config.listener.RedisKeyExpirationListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisKeyExpirationConfig {

    @Value("${spring.redis.database}")
    private int redisDatabase;

    /**
     * Redis消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       RedisKeyExpirationListener redisKeyExpirationListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 监听过期事件频道，数据库1
        container.addMessageListener(redisKeyExpirationListener, new PatternTopic("__keyevent@" + redisDatabase + "__:expired"));

        return container;
    }
}
