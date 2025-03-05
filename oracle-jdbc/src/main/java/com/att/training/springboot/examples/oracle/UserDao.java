package com.att.training.springboot.examples.oracle;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcClient jdbcClient;

    public int count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM users")
                .query(Integer.class)
                .single();
    }


    public User findById(int id) {
        return jdbcClient.sql("""
                        SELECT * FROM users
                        WHERE id = ?
                        """)
                .param(id)
                .query(this::mapToUser)
                .single();
    }

    public Optional<User> tryFindById(int id) {
        return jdbcClient.sql("""
                        SELECT * FROM users
                        WHERE id = ?
                        """)
                .param(id)
                .query(this::mapToUser)
                .optional();
    }

    public User findByIdOrNull(int id) {
        return jdbcClient.sql("""
                        SELECT * FROM users
                        WHERE id = :id
                        """)
                .param("id", id)
                .query(User.class)
                .optional()
                .orElse(null);
    }

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM users")
                .query(this::mapToUser)
                .list();
    }

    public Set<String> findDistinctNames() {
        return jdbcClient.sql("SELECT DISTINCT name FROM users")
                .query(String.class)
                .set();
    }

    public void update(User user) {
        jdbcClient.sql("""
                        UPDATE users
                        SET name = :name, email = :email
                        WHERE id = :id
                        """)
                .param("name", user.name())
                .param("email", user.email())
                .param("id", user.id())
                .update();
    }

    public void updateWithDtoAsParamSource(User user) {
        jdbcClient.sql("""
                        UPDATE users
                        SET name = :name, email = :email
                        WHERE id = :id
                        """)
                .paramSource(user)
                .update();
    }

    private User mapToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email")
        );
    }
}

