package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.GROUP_ID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordConsumer {
    private final RecordProcessor recordProcessor;

    @KafkaListener(topics = Kafka.MAIN_TOPIC)
    public void consumeAsString(String payload, @Header(GROUP_ID) String groupId) {
        log.info("#consumeAsString - consumed payload: {}, groupId: {} ", payload, groupId);
        recordProcessor.process(payload, groupId);
    }

    @KafkaListener(topics = Kafka.MAIN_TOPIC, groupId = "spring-examples-kafka-string", autoStartup = "false")
    public void consumeAsConsumerRecord(ConsumerRecord<?, ?> consumerRecord) {
        log.info("#consumeAsConsumerRecord - consumed consumerRecord: {}, headers: {}", consumerRecord, consumerRecord.headers());
        recordProcessor.process(consumerRecord);
    }
}
