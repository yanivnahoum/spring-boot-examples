package com.att.training.springboot.examples;

import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProcessor {
    public void onMessage(ServiceBusReceivedMessageContext messageContext) {
        ServiceBusReceivedMessage message = messageContext.getMessage();
        log.info("Processing message. Id: {}, Sequence #: {}. Contents: {}", message.getMessageId(),
                message.getSequenceNumber(), message.getBody());
    }

    public void onError(ServiceBusErrorContext errorContext) {
        log.info("Error when receiving messages from namespace: '{}'. Entity: '{}",
                errorContext.getFullyQualifiedNamespace(), errorContext.getEntityPath());
    }
}
