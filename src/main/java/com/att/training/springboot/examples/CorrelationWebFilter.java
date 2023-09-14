package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

@Component
@Slf4j
public class CorrelationWebFilter implements WebFilter {
    public static final String CORRELATION_ID_KEY = "CORRELATION_ID";
    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var correlationId = UUID.randomUUID().toString();
        log.info("[{}] Incoming request: {}", correlationId, exchange.getRequest().getPath());
        return chain.filter(exchange)
                .contextWrite(Context.of(CORRELATION_ID_KEY, correlationId))
                .contextCapture();
    }
}
