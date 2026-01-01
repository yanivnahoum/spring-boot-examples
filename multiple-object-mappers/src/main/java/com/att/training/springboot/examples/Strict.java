package com.att.training.springboot.examples;

import org.springframework.beans.factory.annotation.Qualifier;
import tools.jackson.databind.json.JsonMapper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifies a strict {@link JsonMapper}, one that fails
 * on deserialization of unknown properties.
 */
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@Documented
@Qualifier("strictMapper")
public @interface Strict {}
