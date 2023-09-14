package com.att.training.springboot.examples;

import io.micrometer.context.ContextRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExamplesApplication {

	public static void main(String[] args) {
		ContextRegistry.getInstance()
				.registerThreadLocalAccessor(new CorrelationIdThreadLocalAccessor());
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(SpringBootExamplesApplication.class, args);
	}

}
