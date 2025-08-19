package com.att.training.springboot.examples;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
public class RedisContainerConfig {
    @ServiceConnection
    @Bean
    public RedisContainer redisContainer() {
        return new RedisContainer("redis:6.0-alpine");
    }
}
