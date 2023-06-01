package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final UserResultSetExtractor userResultSetExtractor = new UserResultSetExtractor(userRowMapper);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParamsTemplate;

    public int count() {
        return requireNonNull(jdbcTemplate.queryForObject("SELECT COUNT(1) FROM app.users", Integer.class));
    }

    public boolean exists(int id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("""
                SELECT EXISTS (
                    SELECT 1
                    FROM app.users
                    WHERE id = ?
                )
                """, Boolean.class, id));
    }

    public User fetch(int id) {
        return jdbcTemplate.queryForObject("""
                SELECT *
                FROM app.users
                WHERE id = ?
                """, userRowMapper, id);
    }

    public List<Integer> fetchIds() {
        return jdbcTemplate.queryForList("SELECT id FROM app.users", Integer.class);
    }

    public List<User> fetchAll() {
        return jdbcTemplate.query("SELECT * FROM app.users", userRowMapper);
    }

    public List<User> fetchAllWithIdGreaterThan(int id) {
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users
                WHERE id > ?
                """, userRowMapper, id);
    }

    public List<User> fetchAllWithIdBetween(int startInclusive, int endInclusive) {
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users
                WHERE id BETWEEN ? AND ?
                """, userRowMapper, startInclusive, endInclusive);
    }

    public List<User> fetchAllWithIdBetweenV2(int startInclusive, int endInclusive) {
        var params = Map.ofEntries(
                entry("startInclusive", startInclusive),
                entry("endInclusive", endInclusive)
        );

        @SuppressWarnings("unused")
        // Or if we have nullable values:
        var nullableParams = new MapSqlParameterSource()
                .addValue("startInclusive", startInclusive)
                .addValue("endInclusive", endInclusive);

        return namedParamsTemplate.query("""
                SELECT *
                FROM app.users
                WHERE id BETWEEN :startInclusive AND :endInclusive
                """, params, userRowMapper);
    }

    public int insert(User user) {
        return jdbcTemplate.update("""
                INSERT INTO app.users(id, first_name, last_name)
                VALUES (?, ?, ?)
                """, user.id(), user.firstName(), user.lastName());
    }

    public int insertV2(User user) {
        return namedParamsTemplate.update("""
                INSERT INTO app.users(id, first_name, last_name)
                VALUES (:id, :firstName, :lastName)
                """, new BeanPropertySqlParameterSource(user));
    }

    public List<User> fetchAllWithEmails() {
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users u
                LEFT JOIN app.emails e ON u.id = e.user_id
                """, userResultSetExtractor);
    }
}
