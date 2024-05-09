package com.att.training.springboot.examples;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class StreamWithIndexTest {
    record Indexed<T>(T value, long index) {}

    @Test
    void mapWithIndex1() {
        var inputs = List.of("a", "b", "c", "d", "e");

        var indexedInputs = new ArrayList<>(inputs.size());
        int index = 0;
        for (var value : inputs) {
            var indexed = new Indexed<>(value, index++);
            indexedInputs.add(indexed);
        }

        assertThat(indexedInputs).containsExactly(
                new Indexed<>("a", 0),
                new Indexed<>("b", 1),
                new Indexed<>("c", 2),
                new Indexed<>("d", 3),
                new Indexed<>("e", 4)
        );
    }

    @Test
    void mapWithIndex2() {
        var inputs = List.of("a", "b", "c", "d", "e");

        var index = new AtomicInteger();
        var indexed = inputs.stream()
                .map(value -> new Indexed<>(value, index.getAndIncrement()))
                .toList();

        assertThat(indexed).containsExactly(
                new Indexed<>("a", 0),
                new Indexed<>("b", 1),
                new Indexed<>("c", 2),
                new Indexed<>("d", 3),
                new Indexed<>("e", 4)
        );
    }

    @Test
    void mapWithIndex() {
        var inputs = List.of("a", "b", "c", "d", "e");

        var indexed = Streams.mapWithIndex(inputs.stream(), Indexed::new)
                .toList();

        assertThat(indexed).containsExactly(
                new Indexed<>("a", 0),
                new Indexed<>("b", 1),
                new Indexed<>("c", 2),
                new Indexed<>("d", 3),
                new Indexed<>("e", 4)
        );
    }
}
