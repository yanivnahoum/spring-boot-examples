package com.att.training.springboot.examples.services.actor;

public record FilmographyEntry(Long id, Long actorId, Long movieId, String role, int yearOfRelease) {}
