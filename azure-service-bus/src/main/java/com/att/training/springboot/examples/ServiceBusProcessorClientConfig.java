package com.att.training.springboot.examples;

import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class ServiceBusProcessorClientConfig {
    @Bean
    ServiceBusRecordMessageListener processMessage(MessageProcessor messageProcessor) {
        return messageProcessor::onMessage;
    }

    @Bean
    ServiceBusErrorHandler processError(MessageProcessor messageProcessor) {
        return messageProcessor::onError;
    }
}

