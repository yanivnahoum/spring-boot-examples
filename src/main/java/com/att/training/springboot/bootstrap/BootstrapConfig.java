package com.att.training.springboot.bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration(proxyBeanMethods = false)
@EnableAsync
public class BootstrapConfig {

    @Bean
    SomeBean someBean() {
        System.out.println("***************** someBean *****************");
        return new SomeBean();
    }
}

