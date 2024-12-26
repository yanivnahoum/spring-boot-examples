package com.att.training.springboot.examples;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record User(@Positive int id, @NotEmpty String name) {}
