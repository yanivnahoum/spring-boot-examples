package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
    private final UserRowMapper userRowMapper;

    @NonNull
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var map = new HashMap<Integer, User>();
        while (rs.next()) {
            var userId = rs.getInt("id");
            var email = rs.getString("email");
            map.computeIfAbsent(userId, k -> mapUser(rs))
                    .emails()
                    .add(email);
        }
        return List.copyOf(map.values());
    }

    @SneakyThrows(SQLException.class)
    private User mapUser(ResultSet rs) {
        return userRowMapper.mapRow(rs, rs.getRow());
    }
}
