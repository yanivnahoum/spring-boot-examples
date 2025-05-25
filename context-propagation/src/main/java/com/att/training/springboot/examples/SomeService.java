package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SomeService {
    private final TaskExecutor taskExecutor;

    void sync() {
        try (var ignored = LogContext.putClosableCorrelationId()) {
            sleepAndLog(Duration.ofSeconds(2));
            sleepAndLog(Duration.ofSeconds(1));
        }
    }

    void async() {
        try (var ignored = LogContext.putClosableCorrelationId()) {
            log.info("#async");
            // We can wrap out runnables with a ContextRunnable:
            var task1 = CompletableFuture.runAsync(new ContextRunnable(() -> sleepAndLog(Duration.ofSeconds(2))), taskExecutor);
            var task2 = CompletableFuture.runAsync(() -> sleepAndLog(Duration.ofSeconds(2)), taskExecutor);
            var task3 = CompletableFuture.runAsync(() -> sleepAndLog(Duration.ofSeconds(1)), taskExecutor);
            CompletableFuture.allOf(task1, task2, task3).join();
        }
    }

    @SneakyThrows(InterruptedException.class)
    private void sleepAndLog(Duration duration) {
        log.info("#sleepAndLog - Going to sleep...!");
        TimeUnit.SECONDS.sleep(duration.toSeconds());
        log.info("#sleepAndLog - done sleeping!");
    }
}

