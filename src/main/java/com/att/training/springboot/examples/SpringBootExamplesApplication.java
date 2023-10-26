package com.att.training.springboot.examples;

import com.att.training.springboot.bootstrap.SomeBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync(proxyTargetClass = true)
public class SpringBootExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExamplesApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(SomeBean someBean, SomeService someService) {
		return args -> System.out.println("Hello, Spring Boot!");
	}
}
