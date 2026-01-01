package com.att.training.springboot.examples;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class UserRestTemplate {
    private final RestTemplate restTemplate;

    public UserRestTemplate(RestTemplateBuilder restTemplateBuilder, UserClientProperties userClientProperties) {
        this.restTemplate = restTemplateBuilder
                .rootUri(userClientProperties.baseUrl())
                .connectTimeout(userClientProperties.connectTimeout())
                .readTimeout(userClientProperties.readTimeout())
                .build();
    }

    public User getUser(long id) {
        return restTemplate.getForObject("/users/{id}", User.class, id);
    }
}
