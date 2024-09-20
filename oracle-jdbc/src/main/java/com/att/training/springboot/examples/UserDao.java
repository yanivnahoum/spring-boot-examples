package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcClient jdbcClient;

    public List<User> findAll() {
        try {
            var tableName = "users";
            return jdbcClient.sql(STR."SELECT * FROM \{tableName}")
                    .query(this::mapToUser)
                    .list();
        } catch (Exception _) {
            return Collections.emptyList();
        }
    }

    private User mapToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
    }
}

