package com.att.training.springboot.examples;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class UserRestTemplate {
    private final RestTemplate restTemplate;

    public UserRestTemplate(RestTemplateBuilder restTemplateBuilder, UserClientProperties userClientProperties) {
        this.restTemplate = restTemplateBuilder
                .rootUri(userClientProperties.baseUrl())
                .setConnectTimeout(userClientProperties.connectTimeout())
                .setReadTimeout(userClientProperties.readTimeout())
                .build();
    }

    public User getUser(long id) {
        return restTemplate.getForObject("/users/{id}", User.class, id);
    }
}
