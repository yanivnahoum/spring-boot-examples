package com.att.training.springboot.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig {
    public static final String USERS_CACHE_NAME = "users";

    // If you need to override the whole configuration (shouldn't be necessary)
    // @Bean
    public RedisCacheConfiguration cacheConfiguration(CacheProperties cacheProperties) {
        var redisCacheProperties = cacheProperties.getRedis();
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .prefixCacheNameWith(redisCacheProperties.getKeyPrefix())
//                .computePrefixWith(cacheName -> cacheName + ":")
                .entryTtl(redisCacheProperties.getTimeToLive())
                .serializeValuesWith(SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(User.class)));
    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(ObjectMapper objectMapper) {
        return builder -> {
            var serializer = GenericJackson2JsonRedisSerializer.builder()
                    .objectMapper(objectMapper.copy())
                    .defaultTyping(true)
                    .build();
            var valueSerializationPair = SerializationPair.fromSerializer(serializer);
            // Simpler option for a single POJO to serialize/deserialize in Redis:
            // var valueSerializationPair = SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(MyOnlyPojo.class));
            var redisCacheConfiguration = builder.cacheDefaults().serializeValuesWith(valueSerializationPair);
            builder.cacheDefaults(redisCacheConfiguration);
        };
    }
}
