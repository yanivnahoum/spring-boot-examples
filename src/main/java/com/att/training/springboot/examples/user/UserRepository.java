package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserDao userDao;

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
        return userDao.findById(id);
    }

    @NonNull
    public Optional<User> optionallyFindById(int id) {
        return Optional.ofNullable(userDao.findById(id));
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
