package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DistinctByKeyTest {
    @Test
    void distinctObjectsUsingEquals() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(1, "Alice"),
                new User(3, "David"),
                new User(2, "Bob")
        );

        var distinctUsers = users.stream()
                .distinct()
                .toList();

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "David")
        );
    }

    @Test
    void distinctObjectsUsingName() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "Alice"),
                new User(4, "David"),
                new User(5, "Bob")
        );

        // This won't work if we want to filter by name
        var distinctUsers = users.stream()
                .distinct()
                .toList();

        assertThat(distinctUsers).contains(
                new User(3, "Alice"),
                new User(5, "Bob")
        );
    }


    @Test
    void distinctObjectsUsingNameImperative() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "Alice"),
                new User(4, "David"),
                new User(5, "Bob")
        );


        var distinctNames = new HashSet<String>();
        var distinctUsers = new ArrayList<User>();
        for (var user : users) {
            var added = distinctNames.add(user.name());
            if (added) {
                distinctUsers.add(user);
            }
        }

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(4, "David")
        );
    }

    @Test
    void distinctObjectsUsingNameFunctional() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "Alice"),
                new User(4, "David"),
                new User(5, "Bob")
        );

        var distinctUsers = users.stream()
                .filter(distinctByName())
                .toList();

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(4, "David")
        );

    }

    private Predicate<User> distinctByName() {
        var distinctNames = new HashSet<String>();
        return user -> distinctNames.add(user.name());
    }

    @Test
    void distinctObjectsUsingNameFunctionalAndGeneric() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "Alice"),
                new User(4, "David"),
                new User(5, "Bob")
        );

        List<User> list = users.stream().toList();

        var distinctUsers = users.stream()
                .filter(distinctByKey(User::name))
                .toList();

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(4, "David")
        );
    }

    private <T, R> Predicate<T> distinctByKey(Function<T, R> keyExtractor) {
        var distinctNames = new HashSet<R>();
        return arg -> distinctNames.add(keyExtractor.apply(arg));
    }


    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    void foo() {
        CompletableFuture.runAsync(this::task1, executor)
                .orTimeout(5, SECONDS)
                .join();
        System.out.println("Done!");
    }

    @Test
    void bar() {
        CompletableFuture.runAsync(() -> System.out.println("Task1"), executor)
                .thenCompose(ignored -> task1Async())
                .orTimeout(5, SECONDS)
                .join();
        System.out.println("Done!");
    }

    private void task1() {
        System.out.println("Task1");
        var task2 = CompletableFuture.runAsync(() -> System.out.println("Task2"), executor);
        var task3 = CompletableFuture.runAsync(() -> System.out.println("Task3"), executor);
        // Wait for all tasks to complete
        CompletableFuture.allOf(task2, task3).join();
    }

    private CompletableFuture<Void> task1Async() {
        var task2 = CompletableFuture.runAsync(() -> System.out.println("Task2"), executor);
        var task3 = CompletableFuture.runAsync(() -> System.out.println("Task3"), executor);
        // Wait for all tasks to complete
        return CompletableFuture.allOf(task2, task3);
    }
}
