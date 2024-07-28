package com.att.training.springboot.examples.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

@Slf4j
public final class LogContext {
    private LogContext() {
        // Instantiation from outside the class is not allowed
    }

    public static void setCorrelationId(UUID correlationId) {
        MDC.put("correlationId", correlationId.toString());
    }

    public static void setOperationName(String operationName) {
        MDC.put("operationName", operationName);
    }

    public static void clear() {
        MDC.clear();
    }
}
