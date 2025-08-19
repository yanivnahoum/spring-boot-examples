package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;

public class TestSpringRedissonApplication {
    static void main(String[] args) {
        SpringApplication.from(SpringRedissonApplication::main)
                .with(RedisContainerConfig.class)
                .run(args);
    }
}
