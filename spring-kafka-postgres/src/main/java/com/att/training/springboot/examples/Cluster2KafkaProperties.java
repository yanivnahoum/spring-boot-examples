package com.att.training.springboot.examples;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.cluster2")
public record Cluster2KafkaProperties(String bootstrapServers) {}
