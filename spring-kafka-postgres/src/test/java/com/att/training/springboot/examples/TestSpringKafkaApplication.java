package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;

public class TestSpringKafkaApplication {
    static void main(String[] args) {
        SpringApplication.from(SpringKafkaApplication::main)
                .with(KafkaConfiguration.class, PostgresConfiguration.class)
                .run(args);
    }
}
