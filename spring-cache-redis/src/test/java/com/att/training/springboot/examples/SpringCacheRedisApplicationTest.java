package com.att.training.springboot.examples;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@WithRedisContainer
class SpringCacheRedisApplicationTest {
    @Autowired
    private MockMvcTester mockMvc;
    @Autowired
    private CacheManager cacheManager;
    @MockitoSpyBean
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
    void givenUserId1_whenGetUserTwice_dbIsAccessedOnlyOnce() {
        int id = 1;
        var result = mockMvc.perform(get("/users/{id}", id));
        assertThat(result).hasStatus(OK);

        result = mockMvc.perform(get("/users/{id}", id));
        assertThat(result).hasStatus(OK);

        verify(userDao).findById(id);
    }

    @Test
    void givenUserId1_whenUpdateUserAndThenGetUser_updatedUserIsReturnedAndDbIsNotAccessed() {
        int id = 1;
        var updatedUser = """
                {
                    "id": %d,
                    "name": "John"
                }""".formatted(id);
        var result = mockMvc.perform(put("/users")
                .content(updatedUser)
                .contentType(APPLICATION_JSON));
        assertThat(result).hasStatus(OK);

        result = mockMvc.perform(put("/users")
                .content(updatedUser)
                .contentType(APPLICATION_JSON));
        assertThat(result).hasStatus(OK)
                .bodyJson().isEqualTo(updatedUser);

        verify(userDao, never()).findById(id);
    }
}
