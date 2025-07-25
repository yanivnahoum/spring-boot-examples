package com.att.training.springboot.examples;

import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsErrorHandler;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsRecordMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class EventHubProcessorClientConfiguration {
    private final EventConsumer eventConsumer;

    @Bean
    public EventHubsRecordMessageListener onEvent() {
        return eventConsumer::consume;
    }

    @Bean
    public EventHubsErrorHandler onError() {
        return eventConsumer::error;
    }

    @Bean
    public CommandLineRunner startProcessor(EventProcessorClient eventProcessorClient) {
        return _ -> {
            eventProcessorClient.start();
            SECONDS.sleep(5); // Allow some time for the processor to start
        };
    }
}
