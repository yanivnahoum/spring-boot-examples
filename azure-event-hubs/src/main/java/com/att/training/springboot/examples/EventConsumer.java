package com.att.training.springboot.examples;

import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final EventProcessor eventProcessor;

    public void consume(EventContext eventContext) {
        log.info("#consume >> Processing event from partition {} with sequence number {} with body: {}",
                eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(),
                eventContext.getEventData().getBodyAsString());
        eventProcessor.processEvent(eventContext.getEventData());
    }

    public void error(ErrorContext errorContext) {
        log.info("#error >> Error occurred in partition processor for partition {}",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    }
}
