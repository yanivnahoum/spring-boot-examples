package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
class ChainingFuturesTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    void bad() {
        CompletableFuture.supplyAsync(this::task1, executor)
                .thenAccept(results -> log.info("results: {}", results))
                .orTimeout(5, SECONDS)
                .join();
        System.out.println("Done!");
    }

    @Test
    void good() {
        CompletableFuture.runAsync(() -> log.info("Task1"), executor)
                .thenComposeAsync(ignored -> task1Async(), executor)
                .thenAccept(results -> log.info("results: {}", results))
                .join();
        System.out.println("Done!");
    }

    private List<String> task1() {
        log.info("Task1");
        var task2 = CompletableFuture.supplyAsync(() -> logAndSupply("Task2"), executor);
        var task3 = CompletableFuture.supplyAsync(() -> logAndSupply("Task3"), executor);
        // Wait for all tasks to complete. (This will block indefinitely)
        CompletableFuture.allOf(task2, task3).join();
        // Now use task2 and task3
        return List.of(task2.join(), task3.join());
    }

    private CompletableFuture<List<String>> task1Async() {
        var task2 = CompletableFuture.supplyAsync(() -> logAndSupply("Task2"), executor);
        var task3 = CompletableFuture.supplyAsync(() -> logAndSupply("Task3"), executor);
        // Wait for all tasks to complete
        return CompletableFuture.allOf(task2, task3)
                // Now use them
                .thenApply(ignored -> List.of(task2.join(), task3.join()));
    }

    private static String logAndSupply(String taskName) {
        log.info("Running {}", taskName);
        return taskName;
    }
}
