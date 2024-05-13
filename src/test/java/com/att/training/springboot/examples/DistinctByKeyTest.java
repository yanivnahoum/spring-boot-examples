package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

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
                new User(2, "Bob")
        );

        // This won't work if we want to filter by name
        var distinctUsers = users.stream()
                .distinct()
                .toList();

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(4, "David")
        );
    }


    @Test
    void distinctObjectsUsingNameImperative() {
        var users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(3, "Alice"),
                new User(4, "David"),
                new User(2, "Bob")
        );


        var distinctNames = new HashSet<String>();
        var distinctUsers = new ArrayList<User>();
        for (var user : users) {
            var added = distinctNames.add(user.name().toLowerCase());
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
                new User(2, "Bob")
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
                new User(2, "Bob")
        );

        var distinctUsers = users.stream()
                .filter(distinctByKey(User::name))
                .toList();

        assertThat(distinctUsers).containsExactly(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(4, "David")
        );


    }

    private <T, R> Predicate<T> distinctByKey(Function<? super T, ? extends R> keyExtractor) {
        var distinctNames = new HashSet<R>();
        return arg -> distinctNames.add(keyExtractor.apply(arg));
    }
}
