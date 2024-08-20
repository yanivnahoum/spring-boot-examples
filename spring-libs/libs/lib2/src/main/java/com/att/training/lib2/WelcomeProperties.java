package com.att.training.lib2;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.welcome")
@Validated
public record WelcomeProperties(@NotEmpty String message, @Positive int times) {
}
