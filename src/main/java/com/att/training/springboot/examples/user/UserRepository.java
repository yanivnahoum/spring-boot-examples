package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserDao userDao;
    private static final List<User> users = List.of(
            new User(1, "Alice"),
            new User(2, "Bob"),
            new User(3, "Carl")
    );
    private static final Map<Integer, User> idToUser = users.stream()
            .collect(toMap(User::id, Function.identity()));

    @NonNull
    public User findById(int id) {
        var user = userDao.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        log.info("#findById - Found {}", user);
        return user;
    }

    @Nullable
    public User findByIdOrNull(int id) {
        return idToUser.get(id);
    }

    @NonNull
    public Optional<User> optionallyFindById(int id) {
        return Optional.ofNullable(idToUser.get(id));
    }

    @NonNull
    public SuccessOrFailure<User> tryFindById(int id) {
        try {
            return Success.of(findById(id));
        } catch (Exception ex) {
            return Failure.of(ex);
        }
    }
}
