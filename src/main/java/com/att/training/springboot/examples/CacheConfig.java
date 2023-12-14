package com.att.training.springboot.examples;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig {
    public static final String USERS_CACHE_NAME = "users";

    @Bean
    public RedisCacheConfiguration cacheConfiguration(CacheProperties cacheProperties) {
        var redisCacheProperties = cacheProperties.getRedis();
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .prefixCacheNameWith(redisCacheProperties.getKeyPrefix())
                .entryTtl(redisCacheProperties.getTimeToLive())
                .serializeValuesWith(SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(User.class)));
    }
}
