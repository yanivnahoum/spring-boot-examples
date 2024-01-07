package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class SpringBootExamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootExamplesApplication.class, args);
    }

    @Bean
    CommandLineRunner logger(AppProperties appProperties) {
        return args -> log.info("name: {}", appProperties.name());
    }
}

@ConfigurationProperties("app")
record AppProperties(FullName name) {}

record FullName(String first, String last) {}

@Component
@ConfigurationPropertiesBinding
class FullNameConverter implements Converter<String, FullName> {
    @Override
    public FullName convert(String source) {
        String[] parts = source.split(" ");
        return new FullName(parts[0], parts[1]);
    }
}
