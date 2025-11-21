package com.jsdc.gsgwxb.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ClassName: ProjectConfigure
 * Description:
 * date: 2024/4/18 20:01
 *
 * @author bn
 */
@Configuration
public class ProjectConfigure {

    /** 设定项目的默认缓存策略  */
    @Bean
    @Primary
    public RedisCacheManager myCacheManager(RedisConnectionFactory redisConnectionFactory){
        // 配置序列化为 json序列化格式
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                ;
        // 初始化缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
    /**
     * 注入日志组件 （从yml文件中配置的方式，打包后有概率无法启动项目且无法解决，故用此方法注入自定义日志组件）
     * @param sqlSessionFactory
     */
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//        sqlSessionFactory.getConfiguration().setLogImpl(MybatisStdOutImpl.class);
    }

}
