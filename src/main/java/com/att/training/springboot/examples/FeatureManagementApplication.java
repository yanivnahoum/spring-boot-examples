package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
@Slf4j
public class FeatureManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureManagementApplication.class, args);
    }
}
