package com.att.training.springboot.examples.user;

public record UserPair(User user1, User user2) {
    public static UserPair empty() {
        return new UserPair(null, null);
    }
}
