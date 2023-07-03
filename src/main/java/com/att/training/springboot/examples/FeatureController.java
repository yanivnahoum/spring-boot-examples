package com.att.training.springboot.examples;

import com.azure.spring.cloud.feature.management.FeatureManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureManager featureManager;

    @GetMapping("check/{feature}")
    Mono<Boolean> isFeatureEnabled(@PathVariable String feature) {
        log.info("#isFeatureEnabled - all features: {}", featureManager.getAllFeatureNames());
        return featureManager.isEnabledAsync(feature);
    }
}
