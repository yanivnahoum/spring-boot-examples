package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
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
    private static final String SELECT_USER_BY_ID = """
            SELECT *
            FROM app.users
            WHERE id = ?
            """;
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final UserResultSetExtractor userResultSetExtractor = new UserResultSetExtractor(userRowMapper);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParamsTemplate;

    public int count() {
        // We use Objects.requireNonNull() since queryForObject returns a nullable object,
        // but this query (count) will never return null
        return requireNonNull(jdbcTemplate.queryForObject("SELECT COUNT(1) FROM app.users", Integer.class));
    }

    public boolean exists(int id) {
        // We use Boolean.TRUE.equals(nullableBoolean) to unbox safely
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("""
                SELECT EXISTS (
                    SELECT 1
                    FROM app.users
                    WHERE id = ?
                )
                """, Boolean.class, id));
    }

    public User findById(int id) {
        // queryForObject expects a single row to be returned and will throw:
        // * an EmptyResultDataAccessException if there are 0 rows
        // * an IncorrectResultSizeDataAccessException if there is more than 1 row
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, userRowMapper, id);
    }

    public User findByIdOrNull(int id) {
        // If not finding the user with the given id is a valid scenario, we can catch and return null:
        try {
            return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByIdOrNullV2(int id) {
        // If not finding the user with the given id is a valid scenario, we can use query - which returns a list,
        // and then map an empty list to null. Multiple results will throw IncorrectResultSizeDataAccessException,
        // and a single result will simply be returned.
        var users = jdbcTemplate.query(SELECT_USER_BY_ID, userRowMapper, id);
        return DataAccessUtils.singleResult(users);
    }

    public List<Integer> findAllIds() {
        return jdbcTemplate.queryForList("SELECT id FROM app.users", Integer.class);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM app.users", userRowMapper);
    }

    public List<User> findAllWithIdGreaterThan(int id) {
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users
                WHERE id > ?
                """, userRowMapper, id);
    }

    public List<User> findAllWithIdBetween(int startInclusive, int endInclusive) {
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users
                WHERE id BETWEEN ? AND ?
                """, userRowMapper, startInclusive, endInclusive);
    }

    public List<User> findAllWithIdBetweenV2(int startInclusive, int endInclusive) {
        // The simplest way to pass named parameters to a NamedParameterJdbcTemplate is like this:
        var params = Map.ofEntries(
                entry("startInclusive", startInclusive),
                entry("endInclusive", endInclusive)
        );

        // Or if we have nullable values:
        @SuppressWarnings("unused")
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
        // BeanPropertySqlParameterSource can be used to map the parameters to their values
        return namedParamsTemplate.update("""
                INSERT INTO app.users(id, first_name, last_name)
                VALUES (:id, :firstName, :lastName)
                """, new BeanPropertySqlParameterSource(user));
    }

    public List<User> findAllWithEmails() {
        // When we have multiple rows for a single logical object, we need to aggregate the information
        // into the object while looping over the result set.
        // A simple RowMapper<T> is not fit for the job - it maps a single row to a single object.
        // A ResultSetExtractor<T>, on the other hand, loops the result set and can handle multiple rows.
        return jdbcTemplate.query("""
                SELECT *
                FROM app.users u
                LEFT JOIN app.emails e ON u.id = e.user_id
                """, userResultSetExtractor);
    }
}
