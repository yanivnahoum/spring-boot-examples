package com.att.training.springboot.examples.config;

import com.att.training.springboot.examples.db.DbContext;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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
    TaskDecorator taskDecorator() {
        return this::propagateContext;
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
