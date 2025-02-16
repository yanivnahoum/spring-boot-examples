package com.att.training.springboot.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifies a strict {@link ObjectMapper}, one that fails
 * on deserialization of unknown properties.
 */
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@Documented
@Qualifier("strictMapper")
public @interface Strict {}
