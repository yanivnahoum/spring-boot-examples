package com.att.training.springboot.examples.services.movie;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/movies")
public interface MovieService {
    @GetExchange("/{id}")
    Movie getMovieById(@PathVariable long id);

    @GetExchange
    List<Movie> getAllMovies();

    @PostExchange
    Movie createMovie(@RequestBody Movie movie);
}
