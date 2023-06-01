package com.att.training.springboot.examples.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao user1Dao;

    @Transactional
    public void create(User user) {
        user1Dao.insert(user);
    }
}
