package com.att.training.springboot.examples.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public sealed interface SuccessOrFailure<T> {
    boolean isError();

    T getOrThrow();
}

@RequiredArgsConstructor
final class Success<T> implements SuccessOrFailure<T> {
    private final T value;

    public static <T> Success<T> of(T value) {
        return new Success<>(value);
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public T getOrThrow() {
        return value;
    }
}

@RequiredArgsConstructor
final class Failure<T> implements SuccessOrFailure<T> {
    @Getter
    private final Throwable throwable;

    public static <T> Failure<T> of(Throwable t) {
        return new Failure<>(t);
    }

    @Override
    public boolean isError() {
        return true;
    }

    @SneakyThrows
    @Override
    public T getOrThrow() {
        throw throwable;
    }
}

