package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDao {
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public User findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.info("#findById - Could not find user with id={} in db", id);
            return null;
        }
    }
}
