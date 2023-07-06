package com.att.training.lib1;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(GreetingProperties.class)
@Configuration(proxyBeanMethods = false)
class GreetingConfig {
    @Bean
    Greeter greeter(GreetingProperties greetingProperties) {
        return new Greeter(greetingProperties);
    }
}
