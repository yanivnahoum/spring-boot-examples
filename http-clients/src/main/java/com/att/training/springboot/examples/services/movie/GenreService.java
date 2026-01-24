package com.att.training.springboot.examples.services.movie;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/genres")
public interface GenreService {
    @GetExchange
    List<Genre> getAllGenres();

    @GetExchange("/{id}")
    Genre getGenreById(@PathVariable long id);

    @GetExchange("/{genreId}/movies")
    List<Movie> getMoviesByGenre(@PathVariable long genreId);

    @PostExchange
    Genre createGenre(@RequestBody Genre genre);
}
