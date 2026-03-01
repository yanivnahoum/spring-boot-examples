package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {
    // To allow lombok to copy this to the c'tor parameter, add it in the lombok.config in the project root
    @Cluster2
    private final KafkaTemplate<String, OrderCreated> orderEventKafkaTemplate;

    public CompletableFuture<String> send(OrderCreated event) {
        log.info("#send - sending order event: {}", event);
        return orderEventKafkaTemplate.send(KafkaTopicNames.ORDER_EVENTS, event.orderId(), event)
                .thenApply(SendResult::toString);
    }
}
