package com.att.training.app1;

import com.att.training.lib2.WelcomeProperties;
import com.att.training.lib2.Welcomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Lib2 does not provide autoconfiguration, so we have to do it ourselves.
 */
@EnableConfigurationProperties(WelcomeProperties.class)
@Configuration(proxyBeanMethods = false)
@Slf4j
class WelcomeConfig {
    @Bean
    Welcomer welcomer(WelcomeProperties welcomeProperties) {
        return new Welcomer(welcomeProperties);
    }

    @Bean
    CommandLineRunner welcome(Welcomer welcomer) {
        return args -> log.info(welcomer.greet());
    }
}
