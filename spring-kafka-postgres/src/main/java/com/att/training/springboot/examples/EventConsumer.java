package com.att.training.springboot.examples;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.att.training.springboot.examples.KafkaConfig.CLUSTER2_LISTENER_CONTAINER_FACTORY;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final EventProcessor eventProcessor;

    @KafkaListener(topics = KafkaTopicNames.PRODUCT_PRICE_CHANGES)
    public void consumeProductPriceChange(@Valid ProductPriceChangedEvent productPriceChangedEvent) {
        log.info("#consumeProductPriceChange - consumed event: {}", productPriceChangedEvent);
        eventProcessor.processProductPriceChanged(productPriceChangedEvent);
    }

    @KafkaListener(topics = KafkaTopicNames.ORDER_EVENTS, containerFactory = CLUSTER2_LISTENER_CONTAINER_FACTORY)
    public void consumeOrderCreated(@Valid OrderCreated orderCreated) {
        log.info("#consumeOrderCreated - consumed event: {}", orderCreated);
        eventProcessor.processOrderCreated(orderCreated);
    }
}
