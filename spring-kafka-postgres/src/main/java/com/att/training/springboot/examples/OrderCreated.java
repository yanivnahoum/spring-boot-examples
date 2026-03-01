package com.att.training.springboot.examples;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderCreated(
        @NotEmpty String orderId,
        @NotEmpty String customerId,
        @Positive int quantity,
        @Positive BigDecimal amount
) {}
