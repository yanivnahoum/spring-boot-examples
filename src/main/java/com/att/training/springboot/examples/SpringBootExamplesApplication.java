package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExamplesApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootExamplesApplication.class, args);
	}
}

@Component
@Slf4j
class Scheduler {

	@Scheduled(fixedRate = 1000)
	public void schedule() {
		log.info("Running scheduled task...");
	}
}
