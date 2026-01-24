package com.att.training.springboot.examples.services.review;

public record AverageRating(Long targetId, String targetType, Double averageScore, Integer totalRatings) {}
