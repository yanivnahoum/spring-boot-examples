package com.att.training.lib1;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(GreetingProperties.class)
@AutoConfiguration
class GreetingAutoConfiguration {
    @Bean
    Greeter greeter(GreetingProperties greetingProperties) {
        return new Greeter(greetingProperties);
    }
}
