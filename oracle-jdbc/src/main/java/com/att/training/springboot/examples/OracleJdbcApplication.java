package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OracleJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(OracleJdbcApplication.class, args);
    }
}
