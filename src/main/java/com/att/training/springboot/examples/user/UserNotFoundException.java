package com.att.training.springboot.examples.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super(String.valueOf(id));
    }
}
