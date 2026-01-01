package com.att.training.springboot.examples;

import org.springframework.boot.cache.autoconfigure.CacheProperties;
import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;
import java.util.List;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig {
    public static final String USERS_CACHE_NAME = "users";

    // If you need to override the whole configuration (shouldn't be necessary)
    // @Bean
    public RedisCacheConfiguration cacheConfiguration(CacheProperties cacheProperties) {
        var redisCacheProperties = cacheProperties.getRedis();
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(User.class)));
        String keyPrefix = redisCacheProperties.getKeyPrefix();
        redisCacheConfiguration = keyPrefix != null
                ? redisCacheConfiguration.prefixCacheNameWith(keyPrefix)
                : redisCacheConfiguration;
//                .computePrefixWith(cacheName -> cacheName + ":")
        Duration timeToLive = redisCacheProperties.getTimeToLive();
        redisCacheConfiguration = timeToLive != null
                ? redisCacheConfiguration.entryTtl(timeToLive)
                : redisCacheConfiguration;

        return redisCacheConfiguration;
    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(JsonMapper jsonMapper) {
        return builder -> {
            PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                    .allowIfSubType(User.class.getPackageName() + ".")
                    .allowIfSubType(List.class)
                    .build();
            var serializer = GenericJacksonJsonRedisSerializer.builder(jsonMapper::rebuild)
                    .enableDefaultTyping(typeValidator)
                    .build();
            var valueSerializationPair = SerializationPair.fromSerializer(serializer);
            // Simpler option for a single POJO to serialize/deserialize in Redis:
            // var valueSerializationPair = SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(jsonMapper, MyOnlyPojo.class));
            // Or for generic types:
//            var javaType = jsonMapper.constructType(new TypeReference<List<User>>() {});
//            var jsonRedisSerializer = new JacksonJsonRedisSerializer<>(jsonMapper, javaType);
//            var valueSerializationPair = SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(jsonMapper, javaType));
            var redisCacheConfiguration = builder.cacheDefaults().serializeValuesWith(valueSerializationPair);
            builder.cacheDefaults(redisCacheConfiguration);
        };
    }
}
