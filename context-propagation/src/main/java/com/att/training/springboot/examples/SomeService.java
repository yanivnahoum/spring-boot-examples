package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SomeService {
    private final Executor ioTaskExecutor;

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
//            var task1 = CompletableFuture.runAsync(new ContextRunnable(() -> sleepAndLog(Duration.ofSeconds(2))), ioTaskExecutor);
            var task1 = CompletableFuture.runAsync(() -> sleepAndLog(Duration.ofSeconds(2)), ioTaskExecutor);
            var task2 = CompletableFuture.runAsync(() -> sleepAndLog(Duration.ofSeconds(1)), ioTaskExecutor);
            CompletableFuture.allOf(task1, task2).join();
        }
    }

    @SneakyThrows(InterruptedException.class)
    private void sleepAndLog(Duration duration) {
        log.info("#sleepAndLog - Going to sleep...!");
        TimeUnit.SECONDS.sleep(duration.toSeconds());
        log.info("#sleepAndLog - done sleeping!");
    }
}

