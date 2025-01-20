package com.att.training.springboot.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration(proxyBeanMethods = false)
public class JacksonConfig {
    @Liberal
    @Bean(defaultCandidate = false)
    ObjectMapper liberalObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.failOnUnknownProperties(false).build();
    }
}