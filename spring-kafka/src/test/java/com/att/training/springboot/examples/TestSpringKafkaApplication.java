package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class TestSpringKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.from(SpringKafkaApplication::main)
                .with(KafkaConfiguration.class)
                .run(args);
    }
}

@TestConfiguration(proxyBeanMethods = false)
class KafkaConfiguration {
    @ServiceConnection
    @Bean
    ConfluentKafkaContainer kafkaContainer() {
        return new ConfluentKafkaContainer("confluentinc/cp-kafka:7.9.4");
    }
}

@Target(TYPE)
@Retention(RUNTIME)
@Import(KafkaConfiguration.class)
@interface WithKafka {}
