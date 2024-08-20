package com.att.training.lib1;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.greeting")
@Validated
public record GreetingProperties(@NotEmpty String message, @Positive int times) {
}
