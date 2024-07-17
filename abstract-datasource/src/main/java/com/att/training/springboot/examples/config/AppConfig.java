package com.att.training.springboot.examples.config;

import com.att.training.springboot.examples.db.DbContext;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.concurrent.Executor;

@Configuration(proxyBeanMethods = false)
public class AppConfig {
    @Bean
    CommonsRequestLoggingFilter requestLoggingFilter() {
        var loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeHeaders(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(200);
        return loggingFilter;
    }

    @Bean
    public Executor ioTaskExecutor() {
        var coreCount = Runtime.getRuntime().availableProcessors() * 64;
        return buildExecutor(coreCount);
    }

    private ThreadPoolTaskExecutor buildExecutor(int coreCount) {
        var taskExecutor = new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(coreCount)
                .maxPoolSize(coreCount)
                .threadNamePrefix("io-pool-")
                .taskDecorator(this::propagateContext)
                .build();
        taskExecutor.setDaemon(true);
        return taskExecutor;
    }

    @NonNull
    private Runnable propagateContext(Runnable runnable) {
        var region = DbContext.getRegion();
        return () -> {
            DbContext.setRegion(region);
            try {
                runnable.run();
            } finally {
                DbContext.clear();
            }
        };
    }
}
