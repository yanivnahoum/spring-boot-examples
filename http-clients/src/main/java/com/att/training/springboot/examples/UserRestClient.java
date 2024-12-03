package com.att.training.springboot.examples;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserRestClient {
    private final RestClient restClient;

    public UserRestClient(RestClient.Builder restClientBuilder, UserClientProperties userClientProperties) {
        this.restClient = restClientBuilder
                .baseUrl(userClientProperties.baseUrl())
                .requestFactory(buildRequestFactory(userClientProperties))
                .build();
    }

    private ClientHttpRequestFactory buildRequestFactory(UserClientProperties userClientProperties) {
        ClientHttpRequestFactorySettings requestFactorySettings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(userClientProperties.connectTimeout())
                .withReadTimeout(userClientProperties.readTimeout());
        return ClientHttpRequestFactoryBuilder.httpComponents()
                .build(requestFactorySettings);
    }

    public User getUser(long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
