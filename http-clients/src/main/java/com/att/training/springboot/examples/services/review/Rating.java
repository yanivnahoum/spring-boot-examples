package com.att.training.springboot.examples.services.review;

/**
 * @param targetType "MOVIE" or "ACTOR"
 */
public record Rating(Long id, Long userId, Long targetId, String targetType, Double score) {}
