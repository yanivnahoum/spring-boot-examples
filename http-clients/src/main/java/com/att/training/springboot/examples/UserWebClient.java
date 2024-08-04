package com.att.training.springboot.examples;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;

public class UserWebClient {
    private final WebClient webClient;

    public UserWebClient(WebClient.Builder webClientBuilder, UserClientProperties userClientProperties) {
        this.webClient = webClientBuilder
                .baseUrl(userClientProperties.baseUrl())
                .clientConnector(buildClientConnector(userClientProperties))
                .build();
    }

    private ReactorClientHttpConnector buildClientConnector(UserClientProperties userClientProperties) {
        HttpClient httpClient = HttpClient.create()
                .option(CONNECT_TIMEOUT_MILLIS, Math.toIntExact(userClientProperties.connectTimeout().toMillis()))
                .responseTimeout(userClientProperties.readTimeout());
        return new ReactorClientHttpConnector(httpClient);
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
