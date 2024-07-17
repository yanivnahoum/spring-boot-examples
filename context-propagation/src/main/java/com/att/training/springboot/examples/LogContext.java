package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

@Slf4j
public final class LogContext {
    private static final String CORRELATION_ID_KEY = "correlationId";

    private LogContext() {
        // Instantiation from outside the class is not allowed
    }

    public static void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID_KEY, correlationId);
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID_KEY);
    }

    public static MDC.MDCCloseable putClosableCorrelationId() {
        return MDC.putCloseable(CORRELATION_ID_KEY, UUID.randomUUID().toString());
    }

    public static void clear() {
        MDC.clear();
    }
}
