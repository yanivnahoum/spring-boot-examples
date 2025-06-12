package com.att.training.springboot.examples;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final EventProcessor eventProcessor;

    @KafkaListener(topics = KafkaTopicNames.PRODUCT_PRICE_CHANGES)
    public void consume(@Valid ProductPriceChangedEvent productPriceChangedEvent) {
        log.info("#consume - consumed event: {}", productPriceChangedEvent);
        eventProcessor.process(productPriceChangedEvent);
    }
}
