package com.att.training.springboot.examples;

import org.springframework.boot.task.ThreadPoolTaskExecutorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AsyncConfig {
    @Bean
    public ThreadPoolTaskExecutorCustomizer taskDecorator() {
        return taskExecutor -> taskExecutor.setTaskDecorator(this::propagateLogContext);
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
