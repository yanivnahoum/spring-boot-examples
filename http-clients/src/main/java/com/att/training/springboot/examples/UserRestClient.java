package com.att.training.springboot.examples;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
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
        ClientHttpRequestFactorySettings requestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(userClientProperties.connectTimeout())
                .withReadTimeout(userClientProperties.readTimeout());
        return ClientHttpRequestFactories.get(requestFactorySettings);
    }

    public User getUser(long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
