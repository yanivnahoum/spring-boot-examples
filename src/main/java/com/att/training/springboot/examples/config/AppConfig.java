package com.att.training.springboot.examples.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
public class AppConfig {}
