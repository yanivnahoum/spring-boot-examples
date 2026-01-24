package com.att.training.springboot.examples.services.review;

import java.time.LocalDateTime;

public record Review(
        Long id, Long userId, Long movieId, String title,
        String comment, LocalDateTime createdDate
) {}
