package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.lang.Nullable;

import java.util.Optional;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Slf4j
@RequiredArgsConstructor
class UserDao {
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final JdbcClient jdbcClient;

    @Nullable
    User findUserById() {
        var users = jdbcClient.sql("SELECT id, name FROM users")
                .query(userRowMapper)
                .list();
        return singleResult(users);
    }

    Optional<User> findUserById2() {
        return jdbcClient.sql("SELECT id, name FROM users")
                .query(userRowMapper)
                .optional();
    }


    // Optionals should not typically be used as method parameters
    void incorrectUseOfOptional(Optional<User> user) {
        user.ifPresentOrElse(
                usr -> log.info("Got user: {}", usr),
                () -> log.info("No user found")
        );

        // We don't have this (yet) in java
        // var streetNum = person?.address?.street?.number;
//        Optional.ofNullable(person)
//                .map(Person::address())
//                .map(Address::street())
//                .map(Street::number())
//                .orElse(null);
    }
}

@Slf4j
@RequiredArgsConstructor
class UserService {
    private UserDao userDao;

    User findUserById() {
        var user = userDao.findUserById();
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    User findUserById2() {
        return userDao.findUserById2()
                .orElseThrow(UserNotFoundException::new);
    }

    public void doSomething() {
        User user = getUser(1);
        if (user != null) {
            log.info("Got user: {}", user);
        }

        // Not this:
        User anotherUser = getUser(2);
        Optional.ofNullable(anotherUser)
                .ifPresent(usr -> log.info("Got user: {}", usr));

        // Or this:
        var user1 = getUser(3);
        var user3 = Optional.ofNullable(user1)
                .orElseGet(() -> new User(3, "Jane"));
        // Instead, just use the ternary operator:
        user3 = user != null ? user1 : new User(3, "Jane");
        log.info("Got another user: {}", user3);
    }

    private User getUser(int i) {
        return i > 0 ? new User(1, "John") : null;
    }
}
