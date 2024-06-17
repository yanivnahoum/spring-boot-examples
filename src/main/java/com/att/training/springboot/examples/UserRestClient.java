package com.att.training.springboot.examples;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserRestClient {
    private final RestClient restClient;

    public UserRestClient(RestClient.Builder restClientBuilder, UserClientProperties userClientProperties) {
        this.restClient = restClientBuilder
                .baseUrl(userClientProperties.baseUrl())
                .build();
    }

    public User getUser(long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
