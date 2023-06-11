package com.att.training.springboot.examples.user;

import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Repository
@Slf4j
public class UserRepository {
    private static final List<User> users = List.of(
            new User(1, "Alice"),
            new User(2, "Bob"),
            new User(3, "Carl")
    );
    private static final Map<Integer, User> idToUser = users.stream()
            .collect(toMap(User::id, Function.identity()));

    @NonNull
    public User findById(int id) {
        var user = idToUser.get(id);
        if (user != null) {
            log.info("#findById - Found {}", user);
            return user;
        }
        throw new UserNotFoundException(id);
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
//            log.warn("error", ex);
            throw new InvalidUserException("Id is greater than 100", ex);
            return Failure.of(ex);
        }
    }
}

@StandardException
class InvalidUserException extends RuntimeException {


}
