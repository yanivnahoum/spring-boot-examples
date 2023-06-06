package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getById(int id) {
        log.info("#getById - Attempting to fetch from repo user {}", id);
        return userRepository.findById(id);
    }
}
