package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.att.training.springboot.examples.CacheConfig.USERS_CACHE_NAME;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    @Cacheable(USERS_CACHE_NAME)
    public User fetch(int id) {
        log.info("#fetch >> Fetching user {}", id);
        return userDao.findById(id);
    }

    @Cacheable(cacheNames = USERS_CACHE_NAME, key = "T(java.util.List).copyOf(#ids)")
    public List<User> fetchMany(List<Integer> ids) {
        log.info("#fetchMany >> Fetching users {}", ids);
        return ids.stream()
                .map(userDao::findById)
                // Workaround for issue with JDK-internal lists: https://github.com/spring-projects/spring-data-redis/issues/2697
                .collect(toCollection(ArrayList::new));
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
