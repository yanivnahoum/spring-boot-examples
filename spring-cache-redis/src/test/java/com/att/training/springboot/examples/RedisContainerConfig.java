package com.att.training.springboot.examples;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@TestConfiguration(proxyBeanMethods = false)
public class RedisContainerConfig {
    @SuppressWarnings("resource")
    @ServiceConnection
    @Bean
    public RedisContainer redisContainer() {
        return new RedisContainer("redis:6.0-alpine").withReuse(true);
    }
}

@Target(TYPE)
@Retention(RUNTIME)
@Import(RedisContainerConfig.class)
@interface WithRedisContainer {}
