package com.att.training.springboot.examples;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toMap;

@Repository
@Slf4j
public class UserDao {
    private static final Map<Integer, User> idToUser = Stream.of(
                    new User(1, "Alice"),
                    new User(2, "Bob"),
                    new User(3, "Charlie"))
            .collect(toMap(User::id, Function.identity()));

    @SneakyThrows(InterruptedException.class)
    public User findById(int id) {
        log.info("#findById - fetching user from db, id={}", id);
        SECONDS.sleep(1);
        return idToUser.get(id);
    }

    public void update(User user) {
        log.info("#update - updating user in db, user={}", user);
        idToUser.put(user.id(), user);
    }

    public void delete(int id) {
        log.info("#delete - deleting user from db, id={}", id);
        idToUser.remove(id);
    }
}
