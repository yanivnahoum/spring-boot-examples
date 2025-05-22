package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RecordProcessor {
    public void process(String payload, String groupId) {
        log.info("#process - processing payload: {}, groupId: {}", payload, groupId);
    }

    public void process(ConsumerRecord<?, ?> consumerRecord) {
        log.info("#process - processing ConsumerRecord: {}, headers: {}", consumerRecord, consumerRecord.headers());
    }
}

