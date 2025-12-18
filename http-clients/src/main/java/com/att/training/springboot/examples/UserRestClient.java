package com.att.training.springboot.examples;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.http.client.HttpComponentsClientHttpRequestFactoryBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserRestClient {
    private final RestClient restClient;

    public UserRestClient(RestClient.Builder restClientBuilder, UserClientProperties userClientProperties,
                          HttpComponentsClientHttpRequestFactoryBuilder requestFactoryBuilder,
                          ClientHttpRequestFactorySettings requestFactorySettings) {
        this.restClient = restClientBuilder
                .baseUrl(userClientProperties.baseUrl())
                .requestFactory(buildRequestFactory(requestFactoryBuilder, requestFactorySettings, userClientProperties))
                .defaultStatusHandler(HttpStatusCode::isError, this::handleError)
                .build();
    }

    private ClientHttpRequestFactory buildRequestFactory(HttpComponentsClientHttpRequestFactoryBuilder requestFactoryBuilder,
                                                         ClientHttpRequestFactorySettings requestFactorySettings,
                                                         UserClientProperties userClientProperties) {
        var modifiedSettings = requestFactorySettings.withConnectTimeout(userClientProperties.connectTimeout())
                .withReadTimeout(userClientProperties.readTimeout());
        return requestFactoryBuilder.build(modifiedSettings);
    }

    @SneakyThrows(IOException.class)
    private void handleError(HttpRequest httpRequest, ClientHttpResponse clientHttpResponse) {
        throw new MyCustomException(httpRequest.getURI(), clientHttpResponse.getStatusCode());
    }

    private void customizeConnectionPool(PoolingHttpClientConnectionManagerBuilder pool, UserClientProperties userClientProperties) {
        pool.setMaxConnPerRoute(userClientProperties.maxConnectionsPerRoute());
        pool.setMaxConnTotal(userClientProperties.maxConnectionsTotal());
    }

    public User get(long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new MyCustomException(request.getURI(), response.getStatusCode());
                })
                .body(User.class);
    }

    public List<User> getAll() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void update(User user) {
        restClient.patch()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(user)
                .retrieve()
                .toBodilessEntity();
    }
}

@Getter
class MyCustomException extends RuntimeException {
    private final URI uri;
    private final HttpStatusCode statusCode;

    public MyCustomException(URI uri, HttpStatusCode statusCode) {
        super("Failed to get user from " + uri + ". Status code: " + statusCode);
        this.uri = uri;
        this.statusCode = statusCode;
    }
}
