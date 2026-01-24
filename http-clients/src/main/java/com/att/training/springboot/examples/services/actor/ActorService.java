package com.att.training.springboot.examples.services.actor;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/actors")
public interface ActorService {
    @GetExchange("/{id}")
    Actor getActorById(@PathVariable long id);

    @GetExchange
    List<Actor> getAllActors(@RequestParam MultiValueMap<String, String> params);

    @PostExchange
    Actor createActor(@RequestBody Actor actor);
}
