package com.att.training.springboot.examples;

import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public @interface WithPostgresContainer {
}
