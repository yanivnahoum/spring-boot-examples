package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {
    public List<User> findAll() {
        return List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(1, "Bob", "bob@gmail.com")
        );
    }
}

