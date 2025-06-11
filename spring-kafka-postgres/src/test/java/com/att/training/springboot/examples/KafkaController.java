package com.att.training.springboot.examples;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaTemplate<String, ProductPriceChangedEvent> kafkaTemplate;

    @PutMapping("/produce")
    @ResponseStatus(ACCEPTED)
    public CompletableFuture<String> produce(@Valid @RequestBody ProductPriceChangedEvent event) {
        return kafkaTemplate.send(KafkaTopicNames.PRODUCT_PRICE_CHANGES, event.code(), event)
                .thenApply(SendResult::toString);
    }
}
