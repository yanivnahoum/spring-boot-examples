package com.att.training.springboot.examples.aop;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class TimedAspectTest {

    @Autowired private TargetClass targetClass;

    @Test
    void runAnnotatedMethod() {
        targetClass.annotatedMethod();
    }

    @Test
    void runNonAnnotatedMethodInAnnotatedClass() {
        targetClass.nonAnnotatedMethod();
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAspectJAutoProxy
    @Import({TimedAspect.class, TargetClass.class})
    static class Config { }

    @Timed
    static class TargetClass {

        void nonAnnotatedMethod() {
            sleep();
        }

        @Timed
        void annotatedMethod() {
            sleep();
        }

        @SneakyThrows
        private static void sleep() {
            Thread.sleep(100);
        }
    }
}