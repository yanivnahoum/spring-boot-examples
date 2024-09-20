package com.att.training.springboot.examples;

import com.azure.spring.cloud.feature.management.filters.FeatureFilter;
import com.azure.spring.cloud.feature.management.models.FeatureFilterEvaluationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomFeatureFilter implements FeatureFilter {
    private static final String PARAM_KEY = "param1";

    @Override
    public boolean evaluate(FeatureFilterEvaluationContext context) {
        int paramValue = (int) context.getParameters().getOrDefault(PARAM_KEY, -1);
        log.info("#evaluate invoked: paramValue={}", paramValue);
        return paramValue < 10;
    }
}
