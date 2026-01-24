package com.att.training.springboot.examples.services.review;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/ratings")
public interface RatingService {
    @GetExchange("/movie/{movieId}/average")
    AverageRating getMovieAverageRating(@PathVariable long movieId);

    @GetExchange("/actor/{actorId}/average")
    AverageRating getActorAverageRating(@PathVariable long actorId);

    @PostExchange
    Rating submitRating(@RequestBody Rating rating);

    @DeleteExchange("/{ratingId}")
    void deleteRating(@PathVariable long ratingId);

    @GetExchange("/user/{userId}")
    List<Rating> getUserRatings(@PathVariable long userId);
}
