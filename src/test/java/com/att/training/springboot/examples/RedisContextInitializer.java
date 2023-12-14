package com.att.training.springboot.examples;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RedisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @SuppressWarnings("resource")
    private static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.0-alpine"))
            .withExposedPorts(6379)
            .withCommand("redis-server", "--requirepass", "dummy")
            .withReuse(true);

    static {
        redis.start();
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues values = TestPropertyValues.of(
                "spring.redis.port=%d".formatted(redis.getFirstMappedPort())
        );
        values.applyTo(applicationContext);
    }
}

@Target(TYPE)
@Retention(RUNTIME)
@ContextConfiguration(initializers = RedisContextInitializer.class)
@interface WithRedisContainer {}
