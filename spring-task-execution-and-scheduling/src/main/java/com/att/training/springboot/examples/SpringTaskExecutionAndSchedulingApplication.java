package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When task scheduling is enabled (via @EnableScheduling) we have 2 TaskExecutor beans: </br>
 * <ol>
 *     <li>applicationTaskExecutor: defined by method 'applicationTaskExecutor' in TaskExecutorConfigurations.TaskExecutorConfiguration</li>
 *     <li>taskScheduler: defined by method 'taskScheduler' in TaskSchedulingConfigurations.TaskSchedulerConfiguration</li>
 * </ol>
 * In order to inject the TaskExecutor bean, we can use the following approaches:
 *
 * <ol>
 *     <li>Inject the TaskExecutor bean with a specific name (applicationTaskExecutor)</li>
 *     <li>Inject the TaskExecutor bean with a @Qualifier annotation</li>
 *     <li>Inject the TaskExecutor bean with a custom qualifier meta-annotation</li>
 * </ol>
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@Slf4j
public class SpringTaskExecutionAndSchedulingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTaskExecutionAndSchedulingApplication.class, args);
    }

    @Bean
    public CommandLineRunner injectWithSpecificName(TaskExecutor applicationTaskExecutor) {
        return _ -> log.info("TaskExecutor applicationTaskExecutor bean is available: {}", applicationTaskExecutor != null);
    }

    @Bean
    public CommandLineRunner injectWithQualifier(@Qualifier(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME) TaskExecutor taskExecutor) {
        return _ -> log.info("TaskExecutor <any-name> bean with @Qualifier is available: {}", taskExecutor != null);
    }

    @Bean
    public CommandLineRunner injectWithCustomQualifier(@ApplicationTaskExecutor TaskExecutor someExecutor) {
        return _ -> log.info("TaskExecutor <any-name> bean with @ApplicationTaskExecutor qualifier is available: {}", someExecutor != null);
    }
}

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
@interface ApplicationTaskExecutor {
}
