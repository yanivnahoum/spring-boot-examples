package com.att.training.lib2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "app.welcome")
@Validated
public record WelcomeProperties(@NotEmpty String message, @Positive int times) {
}
