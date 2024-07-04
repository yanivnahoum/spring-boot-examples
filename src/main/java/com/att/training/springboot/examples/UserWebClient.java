package com.att.training.springboot.examples;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserWebClient {
    private final WebClient webClient;

    public UserWebClient(WebClient.Builder webClientBuilder, UserClientProperties userClientProperties) {
        this.webClient = webClientBuilder
                .baseUrl(userClientProperties.baseUrl())
                .build();
    }

    public Mono<User> getUserAsync(long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public User getUser(long id) {
        return getUserAsync(id).block();
    }
}
