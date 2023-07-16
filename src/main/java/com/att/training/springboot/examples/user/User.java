package com.att.training.springboot.examples.user;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record User(
        int id,
        String firstName,
        String lastName,
        List<String> emails
) {
    public User {
        if (emails == null) {
            emails = new ArrayList<>();
        }
    }
}
