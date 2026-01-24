package com.att.training.springboot.examples.services.actor;

import java.time.LocalDate;

public record Actor(Long id, String name, String birthCountry, LocalDate birthDate) {}
