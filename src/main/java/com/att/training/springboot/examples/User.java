package com.att.training.springboot.examples;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record User(@Positive int id, @NotBlank String name) {}
