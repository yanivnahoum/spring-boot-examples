package com.att.training.springboot.examples.user;

import com.att.training.springboot.examples.db.context.WithDbContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final User1Dao user1Dao;
    private final User2Dao user2Dao;
    private final Executor ioTaskExecutor;

    @Transactional
    public void create(User user) {
        user1Dao.insert(user);
        user2Dao.insert(user);
    }

    @WithDbContext
    public CompletableFuture<Void> createAsync(User user) {
        var insertIntoUser1 = CompletableFuture.runAsync(() -> user1Dao.insert(user), ioTaskExecutor);
        var insertIntoUser2 = CompletableFuture.runAsync(() -> user2Dao.insert(user), ioTaskExecutor);
        return CompletableFuture.allOf(insertIntoUser1, insertIntoUser2);
    }
}
