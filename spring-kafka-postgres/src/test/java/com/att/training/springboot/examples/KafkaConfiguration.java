package com.att.training.springboot.examples;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@TestConfiguration(proxyBeanMethods = false)
class KafkaConfiguration {
    private static final String IMAGE_NAME = "confluentinc/cp-kafka:7.9.6";

    @ServiceConnection
    @Bean
    ConfluentKafkaContainer kafkaContainer() {
        return new ConfluentKafkaContainer(IMAGE_NAME);
    }

    @Cluster2
    @Bean
    ConfluentKafkaContainer kafkaCluster2Container() {
        return new ConfluentKafkaContainer(IMAGE_NAME);
    }

    @Bean
    DynamicPropertyRegistrar cluster2KafkaProperties(@Cluster2 ConfluentKafkaContainer kafkaContainer) {
        return registry -> registry.add("app.kafka.cluster2.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}

@Target(TYPE)
@Retention(RUNTIME)
@Import(KafkaConfiguration.class)
@interface WithKafka {}
