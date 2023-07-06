package com.att.training.lib1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "app.greeting")
@Validated
public record GreetingProperties(@NotEmpty String message, @Positive int times) {
}
