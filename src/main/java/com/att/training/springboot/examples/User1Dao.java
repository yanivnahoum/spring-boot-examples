package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class User1Dao {
    private static final String INSERT = """
            INSERT INTO app.users1(id, name)
            VALUES (?, ?)
            """;
    private final JdbcTemplate jdbcTemplate;

    public void insert(User user) {
        jdbcTemplate.update(INSERT, user.id(), user.name());
    }
}
