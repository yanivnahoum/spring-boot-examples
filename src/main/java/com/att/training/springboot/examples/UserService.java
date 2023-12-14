package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.att.training.springboot.examples.CacheConfig.USERS_CACHE_NAME;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    @Cacheable(USERS_CACHE_NAME)
    public User fetch(int id) {
        return userDao.findById(id);
    }

    @CachePut(cacheNames = USERS_CACHE_NAME, key = "#user.id()")
    public User update(User user) {
        userDao.update(user);
        return user;
    }

    @CacheEvict(cacheNames = USERS_CACHE_NAME)
    public void delete(int id) {
        userDao.delete(id);
    }
}
