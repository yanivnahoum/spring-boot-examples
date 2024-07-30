package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class SpringPropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPropertiesApplication.class, args);
    }

    @Bean
    CommandLineRunner logProperties(OrderClientProperties orderClientProperties, SomeClass someClass) {
        return args -> log.info("{}, {}", orderClientProperties, someClass);
    }
}
