package com.att.training.springboot.examples;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration(proxyBeanMethods = false)
public class AsyncConfig {
    @Bean
    public Executor ioTaskExecutor() {
        var threadPoolSize = 10;
        var taskExecutor = new TaskExecutorBuilder()
                .corePoolSize(threadPoolSize)
                .maxPoolSize(threadPoolSize)
                .threadNamePrefix("io-pool-")
                // Uncomment this to decorate all runnables and propagate the log context
//                .taskDecorator(this::propagateLogContext)
                .build();

        taskExecutor.setDaemon(true);
        return taskExecutor;
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
