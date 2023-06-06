package com.att.training.springboot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExamplesApplication {
	public static void main(String[] args) {
		System.setProperty("org.springframework.boot.logging.LoggingSystem", "none");
		SpringApplication.run(SpringBootExamplesApplication.class, args);
	}

}
