package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.GROUP_ID;

@Component
@Slf4j
public class RecordConsumer {
    @KafkaListener(topics = Kafka.MAIN_TOPIC)
    public void consumeAsConsumerRecord(ConsumerRecord<?, ?> record) {
        log.info("Consumed record: {}, headers: {}", record, record.headers());
    }

    @KafkaListener(topics = Kafka.MAIN_TOPIC, groupId = "spring-examples-kafka-string")
    public void consumeAsString(String record, @Header(GROUP_ID) String groupId) {
        log.info("Consumed record: {}, groupId: {} ", record, groupId);
    }
}
