package com.att.training.springboot.examples.services.review;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/reviews")
public interface ReviewService {
    @GetExchange("/movie/{movieId}")
    List<Review> getReviewsByMovie(@PathVariable Long movieId);

    @GetExchange("/{reviewId}")
    Review getReviewById(@PathVariable Long reviewId);

    @PostExchange
    Review createReview(@RequestBody Review review);

    @DeleteExchange("/{reviewId}")
    void deleteReview(@PathVariable Long reviewId);

    @GetExchange("/user/{userId}")
    List<Review> getReviewsByUser(@PathVariable Long userId);
}
