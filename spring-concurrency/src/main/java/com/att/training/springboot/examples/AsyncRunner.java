package com.att.training.springboot.examples;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
@Slf4j
@Async
public class AsyncRunner {
    @SneakyThrows(InterruptedException.class)
    public CompletableFuture<String> toUpperCase(String input) {
        Thread.sleep(500);
        var upperCase = input.toUpperCase();
        log.info("#toUpperCase - input={}, upperCase={}", input, upperCase);
        return CompletableFuture.completedFuture(upperCase);
    }

    @Async("ioTaskExecutor")
    public <T> CompletableFuture<T> run(Supplier<T> supplier) {
        T result = supplier.get();
        return CompletableFuture.completedFuture(result);
    }
}
