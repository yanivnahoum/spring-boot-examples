package com.att.training.springboot.examples;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final ServiceBusSenderClient serviceBusSenderClient;

    @PostMapping("/publish")
    @ResponseStatus(ACCEPTED)
    void publishMessage(@RequestParam String message) {
        serviceBusSenderClient.sendMessage(new ServiceBusMessage(message));
    }
}
