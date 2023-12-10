package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.allOf;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService {
    private final TaskExecutor taskExecutor;
    private final AsyncRunner asyncRunner;

    public List<String> toUpperCaseSync(List<String> inputs) {
        return inputs.stream()
                .map(AsyncService::toUpperCase)
                .toList();

    }
    public List<String> toUpperCaseWithCfOnCommonPool(List<String> inputs) {
        var futures = inputs.stream()
                .map(this::toUpperCaseWithCfOnCommonPool)
                .toList();

        return waitForAll(futures);
    }
    public List<String> toUpperCaseWithCfOnSpringPool(List<String> inputs) {
        var futures = inputs.stream()
                .map(this::toUpperCaseWithCfOnSpringPool)
                .toList();

        return waitForAll(futures);
    }

    public List<String> toUpperCaseWithSpringAsync(List<String> inputs) {
        var futures = inputs.stream()
                .map(asyncRunner::toUpperCase)
                .toList();

        return waitForAll(futures);
    }
    public CompletableFuture<Void> toUpperCaseWithSpringAsyncOnIoPool(List<String> inputs) {
        var futures = inputs.stream()
                .map(input -> asyncRunner.run(() -> toUpperCase(input)))
                .toList();

        return allOf(futures.toArray(new CompletableFuture<?>[0]));
    }

    @SneakyThrows(InterruptedException.class)
    private static String toUpperCase(String input) {
        Thread.sleep(500);
        var upperCase = input.toUpperCase();
        log.info("#toUpperCase - input={}, upperCase={}", input, upperCase);
        return upperCase;
    }
    private CompletableFuture<String> toUpperCaseWithCfOnCommonPool(String input) {
        return CompletableFuture.supplyAsync(() -> toUpperCase(input));
    }
    private CompletableFuture<String> toUpperCaseWithCfOnSpringPool(String input) {
        return CompletableFuture.supplyAsync(() -> toUpperCase(input), taskExecutor);
    }

    private static List<String> waitForAll(List<CompletableFuture<String>> futures) {
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
