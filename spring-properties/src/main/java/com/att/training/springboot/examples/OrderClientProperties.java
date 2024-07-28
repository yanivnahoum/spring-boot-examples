package com.att.training.springboot.examples;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.order-client")
@Validated
public record OrderClientProperties(
        @NotEmpty String baseUrl,
        @NotNull @DefaultValue("5s") Duration responseTimeout) {
}

@Component
@ToString
@RequiredArgsConstructor
class SomeClass {
    @Value("${app.some-property}")
    private final String someProperty;
}