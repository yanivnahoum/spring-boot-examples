package com.att.training.springboot.examples.user;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Builder
public record User(
        @Positive int id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        List<String> emails
) {
    public User {
        if (emails == null) {
            emails = new ArrayList<>();
        }
    }
}
