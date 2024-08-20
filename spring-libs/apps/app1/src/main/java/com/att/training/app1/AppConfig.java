package com.att.training.app1;

import com.att.training.lib1.Greeter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Since Lib1 provides autoconfiguration, we don't need to do anything except inject the Greeter
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
class AppConfig {
    @Bean
    CommandLineRunner greet(Greeter greeter) {
        return args -> log.info(greeter.greet());
    }
}
