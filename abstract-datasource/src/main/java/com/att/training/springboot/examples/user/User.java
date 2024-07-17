package com.att.training.springboot.examples.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record User(@Positive int id, @NotBlank String name) {}
