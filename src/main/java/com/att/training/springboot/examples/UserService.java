package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final User1Dao user1Dao;
    private final User2Dao user2Dao;

    @Transactional
    public void create(User user) {
        user1Dao.insert(user);
        user2Dao.insert(user);
    }
}
