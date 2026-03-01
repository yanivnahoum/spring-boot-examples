package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.validation.Validator;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class KafkaConfig implements KafkaListenerConfigurer {
    public static final String CLUSTER2_LISTENER_CONTAINER_FACTORY = "cluster2ListenerContainerFactory";
    private final Validator validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }

    @Cluster2
    @Bean(defaultCandidate = false)
    ProducerFactory<String, OrderCreated> cluster2ProducerFactory(KafkaProperties kafkaProperties,
                                                                  Cluster2KafkaProperties cluster2KafkaProperties) {
        var producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster2KafkaProperties.bootstrapServers());
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Cluster2
    @Bean(defaultCandidate = false)
    KafkaTemplate<String, OrderCreated> orderEventKafkaTemplate(@Cluster2 ProducerFactory<String, OrderCreated> cluster2ProducerFactory) {
        return new KafkaTemplate<>(cluster2ProducerFactory);
    }

    @Cluster2
    @Bean(defaultCandidate = false)
    ConsumerFactory<String, OrderCreated> cluster2ConsumerFactory(KafkaProperties kafkaProperties,
                                                                  Cluster2KafkaProperties cluster2KafkaProperties) {
        var consumerProperties = kafkaProperties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster2KafkaProperties.bootstrapServers());
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    @Bean(name = CLUSTER2_LISTENER_CONTAINER_FACTORY)
    ConcurrentKafkaListenerContainerFactory<String, OrderCreated> cluster2ListenerContainerFactory(
            @Cluster2 ConsumerFactory<String, OrderCreated> cluster2ConsumerFactory) {
        var listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, OrderCreated>();
        listenerContainerFactory.setConsumerFactory(cluster2ConsumerFactory);
        return listenerContainerFactory;
    }
}

