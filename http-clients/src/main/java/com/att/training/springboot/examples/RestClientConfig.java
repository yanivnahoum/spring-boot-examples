package com.att.training.springboot.examples;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.boot.autoconfigure.http.client.ClientHttpRequestFactoryBuilderCustomizer;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpComponentsClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration(proxyBeanMethods = false)
public class RestClientConfig {
    @Bean
    public HttpComponentsClientHttpRequestFactoryBuilder httpComponentsClientHttpRequestFactoryBuilder() {
        // These customizations will be shared by all RestClient.Builders
        return ClientHttpRequestFactoryBuilder.httpComponents()
                .withDefaultRequestConfigCustomizer(config -> config.setConnectionRequestTimeout(45, TimeUnit.SECONDS))
                .withConnectionManagerCustomizer(this::customizeConnectionPool);
    }

    // Another option:
    //@Bean
    public ClientHttpRequestFactoryBuilderCustomizer<HttpComponentsClientHttpRequestFactoryBuilder> customizer() {
        return builder -> builder.withDefaultRequestConfigCustomizer(config -> config.setConnectionRequestTimeout(30, TimeUnit.SECONDS))
                .withConnectionManagerCustomizer(this::customizeConnectionPool);
    }

    private void customizeConnectionPool(PoolingHttpClientConnectionManagerBuilder pool) {
        pool.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setValidateAfterInactivity(TimeValue.ofMinutes(1))
                .setTimeToLive(TimeValue.ofMinutes(10))
                .build());
        pool.setMaxConnPerRoute(100);
        pool.setMaxConnTotal(400);
    }
}


