package com.att.training.springboot.examples;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties("app.user")
@Validated
public record UserClientProperties(
        @NotEmpty String baseUrl,
        @NotNull @DefaultValue("10s") Duration connectTimeout,
        @NotNull @DefaultValue("30s") Duration readTimeout,
        @Positive @DefaultValue("5") int maxConnectionsPerRoute,
        @Positive @DefaultValue("25") int maxConnectionsTotal
) {}
