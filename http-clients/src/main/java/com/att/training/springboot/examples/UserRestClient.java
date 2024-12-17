package com.att.training.springboot.examples;

import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.core5.util.TimeValue;
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
        var retryStrategy = new DefaultHttpRequestRetryStrategy(3, TimeValue.ofSeconds(1));
        return ClientHttpRequestFactoryBuilder.httpComponents()
                .withHttpClientCustomizer(httpClient -> httpClient.setRetryStrategy(retryStrategy))
                .build(requestFactorySettings);
    }

    public User getUser(long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
