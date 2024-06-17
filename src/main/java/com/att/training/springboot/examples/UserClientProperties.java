package com.att.training.springboot.examples;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.user")
public record UserClientProperties(String baseUrl) {}
