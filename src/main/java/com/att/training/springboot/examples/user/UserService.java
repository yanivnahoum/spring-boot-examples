package com.att.training.springboot.examples.user;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(int id) {
        return userRepository.findById(id);
    }

    public UserPair getPairById(int id1, int id2) {
        var user1 = userRepository.findById(id1);
        var user2 = userRepository.findById(id2);
        return new UserPair(user1, user2);
    }

    public UserPair getPairByIdWithNull(int id1, int id2) {
        var user1 = userRepository.findByIdOrNull(id1);
        if (user1 != null) {
            var user2 = userRepository.findByIdOrNull(id2);
            if (user2 != null) {
                return new UserPair(user1, user2);
            }
        }
        return UserPair.empty();
    }

    public UserPair getPairByIdWithOptional(int id1, int id2) {
        return userRepository.optionallyFindById(id1)
                .flatMap(user1 -> userRepository.optionallyFindById(id2)
                        .map(user2 -> new UserPair(user1, user2))
                )
                .orElseGet(UserPair::empty);
    }

    public UserPair getPairByIdWithSuccessOrFailure(int id1, int id2) {
        var result1 = userRepository.tryFindById(id1);
        if (!result1.isError()) {
            var result2 = userRepository.tryFindById(id2);
            if (!result2.isError()) {
                return new UserPair(result1.getOrThrow(), result2.getOrThrow());
            }
        }
        return UserPair.empty();
    }

    public UserPair getPairByIdWithSuccessOrFailureInverted(int id1, int id2) {
        var result1 = userRepository.tryFindById(id1);
        if (result1.isError()) {
            return UserPair.empty();
        }
        var result2 = userRepository.tryFindById(id2);
        if (result2.isError()) {
            return UserPair.empty();
        }

        return new UserPair(result1.getOrThrow(), result2.getOrThrow());
    }

    public UserPair getPairByIdWithTry(int id1, int id2) {
        return Try.of(() -> userRepository.findById(id1))
                .flatMap(user1 -> Try.of(() -> userRepository.findById(id2))
                        .map(user2 -> new UserPair(user1, user2))
                )
                .getOrElse(UserPair::empty);
    }
}
