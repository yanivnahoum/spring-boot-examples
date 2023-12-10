package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration(proxyBeanMethods = false)
@Slf4j
public class AsyncConfig {
    @Bean
    TaskExecutorCustomizer taskExecutorCustomizer() {
        return taskExecutor -> taskExecutor.setTaskDecorator(AsyncConfig::propagateContext);
    }

    private static Runnable propagateContext(Runnable runnable) {
        var mdcContext = MDC.getCopyOfContextMap();
        return () -> {
            if (mdcContext != null) {
                MDC.setContextMap(mdcContext);
            }
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
    @ConditionalOnProperty(name = "app.task.execution.io-pool.enabled")
    @Configuration(proxyBeanMethods = false)
    static class ExtraConfiguration {
        @Bean
        TaskExecutor taskExecutor(TaskExecutorBuilder builder) {
            return builder.build();
        }
        @Bean
        TaskExecutor ioTaskExecutor() {
            return new TaskExecutorBuilder()
                    .corePoolSize(8)
                    .maxPoolSize(8)
                    .threadNamePrefix("io-pool-")
                    .taskDecorator(AsyncConfig::propagateContext)
                    .build();
        }
    }
}
