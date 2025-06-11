package com.att.training.springboot.examples;

import java.math.BigDecimal;

public record Product(String code, String name, BigDecimal price) {}
