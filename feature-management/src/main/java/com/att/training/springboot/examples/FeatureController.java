package com.att.training.springboot.examples;

import com.azure.spring.cloud.appconfiguration.config.AppConfigurationRefresh;
import com.azure.spring.cloud.feature.management.FeatureManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureManager featureManager;
    private final ObjectProvider<AppConfigurationRefresh> appConfigurationRefreshProvider;

    @GetMapping("check/{feature}")
    Mono<Boolean> isFeatureEnabled(@PathVariable String feature) {
        log.info("#isFeatureEnabled - all features: {}", featureManager.getAllFeatureNames());
        return featureManager.isEnabledAsync(feature);
    }

    @GetMapping
    Mono<Set<String>> fetchAll() {
        return Mono.justOrEmpty(appConfigurationRefreshProvider.getIfAvailable())
                .flatMap(AppConfigurationRefresh::refreshConfigurations)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(refreshed -> log.info("Refreshed all configurations: {}", refreshed))
                .map(ignored -> featureManager.getAllFeatureNames());
    }
}
