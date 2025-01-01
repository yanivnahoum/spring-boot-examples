package com.att.training.springboot.examples.oracle;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcClient jdbcClient;

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM users")
                .query(this::mapToUser)
                .list();
    }

    private User mapToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
    }
}

