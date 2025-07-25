package com.att.training.springboot.examples;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final EventHubProducerClient eventHubProducerClient;

    @PostMapping("/publish")
    @ResponseStatus(ACCEPTED)
    void publishMessage(@RequestParam String message) {
        var eventData = new EventData(message);
        eventHubProducerClient.send(List.of(eventData));
        log.info("#publishMessage >> Message sent to Event Hub: {}", message);
    }
}
