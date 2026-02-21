package com.att.training.springboot.examples.services;

import com.att.training.springboot.examples.services.actor.ActorService;
import com.att.training.springboot.examples.services.actor.FilmographyService;
import com.att.training.springboot.examples.services.movie.GenreService;
import com.att.training.springboot.examples.services.movie.MovieService;
import com.att.training.springboot.examples.services.review.RatingService;
import com.att.training.springboot.examples.services.review.ReviewService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "movie", types = { MovieService.class, GenreService.class })
@ImportHttpServices(group = "actor", types = { ActorService.class, FilmographyService.class })
@ImportHttpServices(group = "review", types = { RatingService.class, ReviewService.class })
@Configuration(proxyBeanMethods = false)
public class HttpServiceClientConfig {
    // In case we need programmatic configuration of the HTTP client groups
//    @Bean
    RestClientHttpServiceGroupConfigurer httpServiceGroupConfigurer() {
        return groups -> {
            groups.filterByName("actor").forEachClient((_, restClientBuilder) ->
                    restClientBuilder
                            .apiVersionInserter(ApiVersionInserter.useHeader("X-Version"))
                            .defaultApiVersion("1.0")
            );

            groups.forEachClient((group, restClientBuilder) ->
                    restClientBuilder.defaultHeader("service-group", group.name()));

            var token = "ABCDE-12345-FFFFF-67890";
            groups.filterByName("movie")
                    .forEachClient((_, restClientBuilder) ->
                            restClientBuilder.defaultHeaders(headers -> headers.setBearerAuth(token)));
        };
    }
}
