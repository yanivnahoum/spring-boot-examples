package com.att.training.springboot.examples;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
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

    private void customizeConnectionPool(PoolingHttpClientConnectionManagerBuilder pool) {
        pool.setMaxConnPerRoute(100);
        pool.setMaxConnTotal(400);
    }
}


