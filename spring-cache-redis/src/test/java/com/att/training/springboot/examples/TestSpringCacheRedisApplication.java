package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;

public class TestSpringCacheRedisApplication {
    void main(String[] args) {
        SpringApplication.from(SpringCacheRedisApplication::main)
                .with(RedisContainerConfig.class)
                .run(args);
    }
}
