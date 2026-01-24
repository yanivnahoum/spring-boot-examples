package com.att.training.springboot.examples.services.actor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/filmography")
public interface FilmographyService {
    @GetExchange("/actor/{actorId}")
    List<FilmographyEntry> getActorFilmography(@PathVariable long actorId);

    @PostExchange
    FilmographyEntry addFilmographyEntry(@RequestBody FilmographyEntry entry);

    @GetExchange("/actor/{actorId}/roles")
    List<String> getActorRoles(@PathVariable long actorId);

    @GetExchange("/movie/{movieId}/cast")
    List<FilmographyEntry> getMovieCast(@PathVariable long movieId);
}
