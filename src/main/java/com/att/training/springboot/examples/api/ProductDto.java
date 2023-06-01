package com.att.training.springboot.examples.api;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record ProductDto(
        @Positive(groups = OnUpdate.class)
        int id,
        @NotEmpty String name,
        @AssertTrue boolean inStock,
        @NotNull(groups = OnCreate.class)
        @Past
        Instant manufactureDate,
        @NotNull(groups = OnCreate.class)
        @Email
        String email
) {}


interface OnCreate {}
interface OnUpdate {}


