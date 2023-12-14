package com.att.training.springboot.examples;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithRedisContainer
class SpringBootExamplesApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CacheManager cacheManager;
    @SpyBean
    private UserDao userDao;

    @AfterEach
    void clearAllCaches() {
        cacheManager.getCacheNames()
                .stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }

    @Test
    void givenUserId1_whenGetUserTwice_dbIsAccessedOnlyOnce() throws Exception {
        int id = 1;
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk());
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk());
        verify(userDao).findById(id);
    }

    @Test
    void givenUserId1_whenUpdateUserAndThenGetUser_updatedUserIsReturnedAndDbIsNotAccessed() throws Exception {
        int id = 1;
        var updatedUser = """
                {
                    "id": %d,
                    "name": "John"
                }""".formatted(id);
        mockMvc.perform(put("/users")
                        .content(updatedUser)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedUser));
        verify(userDao, never()).findById(id);
    }
}
