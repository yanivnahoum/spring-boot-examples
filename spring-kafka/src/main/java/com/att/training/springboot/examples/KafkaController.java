package com.att.training.springboot.examples;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @PutMapping("/produce/{number}")
    @ResponseStatus(ACCEPTED)
    public CompletableFuture<String> produce(@Positive @PathVariable int number) {
        return kafkaTemplate.send(Kafka.MAIN_TOPIC, number, String.valueOf(number))
                .thenApply(SendResult::toString);
    }
}
