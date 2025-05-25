package com.att.training.springboot.examples;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

@Configuration(proxyBeanMethods = false)
public class AsyncConfig {
    @Bean
    public TaskDecorator taskDecorator() {
        return this::propagateLogContext;
    }

    private Runnable propagateLogContext(Runnable runnable) {
        var correlationId = LogContext.getCorrelationId();
        return () -> {
            LogContext.setCorrelationId(correlationId);
            try {
                runnable.run();
            } finally {
                LogContext.clear();
            }
        };
    }
}
