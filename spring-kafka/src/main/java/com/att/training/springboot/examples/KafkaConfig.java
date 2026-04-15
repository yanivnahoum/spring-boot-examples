package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class KafkaConfig {
    @Bean
    @SuppressWarnings("java:S1452")
        // For this bean to be picked up by the listener container factory, it must be defined as RecordFilterStrategy<?, ?>
        // If we use RecordFilterStrategy<Integer, String>, it won't be applied to the ConcurrentKafkaListenerContainerFactoryConfigurer
        // that is auto configured in KafkaAutoConfiguration and KafkaAnnotationDrivenConfiguration
    RecordFilterStrategy<?, ?> filterStrategy() {
        return (ConsumerRecord<Integer, String> consumerRecord) -> {
            if (consumerRecord.value() == null) {
                return true; // Discard null records (tombstones or deserialization failures)
            }
            int number = consumerRecord.key();
            boolean discard = number % 2 == 0; // Discard even numbers
            if (discard) {
                log.info("#filterStrategy >> Discarding even number: {}", number);
            } else {
                log.info("#filterStrategy >> Accepting odd number: {}", number);
            }
            return discard;
        };
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setAckDiscarded(false);
        return factory;
    }
}
