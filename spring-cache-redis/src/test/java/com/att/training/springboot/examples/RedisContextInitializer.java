package com.att.training.springboot.examples;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RedisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final RedisContainer redis = new RedisContainer("redis:6.0-alpine")
            .withCommand("redis-server", "--requirepass", "dummy")
            .withReuse(true);

    static {
        redis.start();
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues values = TestPropertyValues.of(
                "spring.data.redis.host=%s".formatted(redis.getRedisHost()),
                "spring.data.redis.port=%d".formatted(redis.getRedisPort())
        );
        values.applyTo(applicationContext);
    }
}

@Target(TYPE)
@Retention(RUNTIME)
@ContextConfiguration(initializers = RedisContextInitializer.class)
@interface WithRedisContainer {}
