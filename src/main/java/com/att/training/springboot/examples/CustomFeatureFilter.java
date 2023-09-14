package com.att.training.springboot.examples;

import com.azure.spring.cloud.feature.management.filters.FeatureFilter;
import com.azure.spring.cloud.feature.management.models.FeatureFilterEvaluationContext;
import io.micrometer.context.ThreadLocalAccessor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomFeatureFilter implements FeatureFilter {
    private static final String PARAM_KEY = "param1";

    @Override
    public boolean evaluate(FeatureFilterEvaluationContext context) {
        int paramValue = (int) context.getParameters().getOrDefault(PARAM_KEY, -1);
        log.info("#evaluate invoked: {}", CorrelationIdThreadLocalContext.get());
//        Mono.deferContextual(Mono::just)
//                .map(ctx -> {
//                    log.info("[{}] #evaluate - {}={}", ctx.get(CorrelationWebFilter.CORRELATION_ID), PARAM_KEY, paramValue);
//                    return Mono.empty();
//                })
        return paramValue < 10;
    }
}

@UtilityClass
class CorrelationIdThreadLocalContext {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public void set(String value) {
        holder.set(value);
    }

    public String get() {
        return holder.get();
    }

    public void clear() {
        holder.remove();
    }
}

class CorrelationIdThreadLocalAccessor implements ThreadLocalAccessor<String> {

    public static final String CONTEXT_KEY = "CORRELATION_ID";

    @Override
    @NonNull
    public Object key() {
        return CONTEXT_KEY;
    }

    @Override
    public String getValue() {
        return CorrelationIdThreadLocalContext.get();
    }

    @Override
    public void setValue(@NonNull String value) {
        CorrelationIdThreadLocalContext.set(value);
    }

    @Override
    public void setValue() {
        CorrelationIdThreadLocalContext.clear();
    }
}