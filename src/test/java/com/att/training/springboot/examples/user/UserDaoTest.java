package com.att.training.springboot.examples.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@JdbcTest
@AutoConfigureTestDatabase(replace = NONE)
@Transactional(propagation = NOT_SUPPORTED)
@Import(UserDao.class)
class UserDaoTest {
    private static final String EMAILS_FIELD_NAME = "emails";
    private static final User JOHN = User.builder()
            .id(1)
            .firstName("John")
            .lastName("Doe")
            .emails(List.of("john1@gmail.com", "john2@gmail.com", "john3@gmail.com"))
            .build();
    private static final User MARY = User.builder()
            .id(2).
            firstName("Mary")
            .lastName("Smith")
            .emails(List.of("mary1@gmail.com", "mary2@gmail.com"))
            .build();
    @Autowired
    private UserDao userDao;

    @Test
    void whenCount_thenReturn2() {
        int userCount = userDao.count();
        assertThat(userCount).isEqualTo(2);
    }

    @Test
    void givenUserId1_whenExists_thenReturnTrue() {
        boolean exists = userDao.exists(1);
        assertThat(exists).isTrue();
    }

    @Test
    void givenId1_whenFetch_thenUser1IsReturned() {
        var user = userDao.fetch(1);
        assertThat(user).usingRecursiveComparison()
                .ignoringFields(EMAILS_FIELD_NAME)
                .isEqualTo(JOHN);
    }

    @Test
    void fetchIds() {
        var ids = userDao.fetchIds();
        assertThat(ids).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void whenFetchAll_thenAllUsersReturned() {
        var users = userDao.fetchAll();
        assertThat(users).usingRecursiveFieldByFieldElementComparatorIgnoringFields(EMAILS_FIELD_NAME)
                .containsExactlyInAnyOrder(JOHN, MARY);
    }

    @Test
    void given1_whenFetchAllWithIdGreaterThan_thenUser2IsReturned() {
        var users = userDao.fetchAllWithIdGreaterThan(1);
        assertThat(users).usingRecursiveFieldByFieldElementComparatorIgnoringFields(EMAILS_FIELD_NAME)
                .containsExactly(MARY);
    }

    @Test
    void given1And2_whenFetchAllWithIdBetween_thenUsers1And2AreReturned() {
        var users = userDao.fetchAllWithIdBetween(1, 2);
        assertThat(users).usingRecursiveFieldByFieldElementComparatorIgnoringFields(EMAILS_FIELD_NAME)
                .containsExactlyInAnyOrder(JOHN, MARY);
    }

    @Test
    void given1And2_whenFetchAllWithIdBetweenV2_thenUsers1And2AreReturned() {
        var users = userDao.fetchAllWithIdBetweenV2(1, 2);
        assertThat(users).usingRecursiveFieldByFieldElementComparatorIgnoringFields(EMAILS_FIELD_NAME)
                .containsExactlyInAnyOrder(JOHN, MARY);
    }

    @Transactional(propagation = REQUIRED)
    @Test
    void givenNewUser_whenInsert_thenUserIsInserted() {
        var carl = User.builder()
                .id(3)
                .firstName("Carl")
                .lastName("Doe")
                .build();
        int affectedRows = userDao.insert(carl);
        assertThat(affectedRows).isOne();
    }

    @Transactional(propagation = REQUIRED)
    @Test
    void givenNewUser_whenInsertV2_thenUserIsInserted() {
        var carl = User.builder()
                .id(3)
                .firstName("Carl")
                .lastName("Doe")
                .build();
        int affectedRows = userDao.insertV2(carl);
        assertThat(affectedRows).isOne();
    }

    @Test
    void whenFetchAllWithEmails_thenReturnUsersWithEmails() {
        var users = userDao.fetchAllWithEmails();
        assertThat(users).containsExactlyInAnyOrder(JOHN, MARY);
    }
}
