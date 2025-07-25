package com.att.training.springboot.examples;

import com.azure.messaging.eventhubs.EventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventProcessor {
    public void processEvent(EventData eventData) {
        log.info("#processEvent >> Processing event data: {}", eventData.getBodyAsString());
    }
}
