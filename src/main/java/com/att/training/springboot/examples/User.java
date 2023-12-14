package com.att.training.springboot.examples;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public record User(@Positive int id, @NotEmpty String name) {}
