package com.att.training.springboot.examples;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPriceChangedEvent(@NotEmpty String code, @Positive BigDecimal price) {}
