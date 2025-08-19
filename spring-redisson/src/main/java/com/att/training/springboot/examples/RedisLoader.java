package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLoader {
    private static final User USER = new User(100, "John", "Doe", 30);
    private final RedissonClient redissonClient;
    private final Random random = new Random();

    @Scheduled(fixedRateString = "100ms")
    public void loadRedis() {
        log.info("#loadRedis >> Loading Redis data...");
        redissonClient.getBucket(getKey(), JsonJacksonCodec.INSTANCE).set(USER);
    }

    private String getKey() {
        return "redis-loader:" + random.nextInt(1, 1001);
    }
}
