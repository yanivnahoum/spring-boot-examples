package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.kafka.ConfluentKafkaContainer;

public class TestSpringKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.from(SpringKafkaApplication::main)
                .with(ContainersConfiguration.class)
                .run(args);
    }
}

@TestConfiguration(proxyBeanMethods = false)
class ContainersConfiguration {
    @ServiceConnection
    @Bean
    ConfluentKafkaContainer kafkaContainer() {
        return new ConfluentKafkaContainer("confluentinc/cp-kafka:7.9.1");
    }
}
