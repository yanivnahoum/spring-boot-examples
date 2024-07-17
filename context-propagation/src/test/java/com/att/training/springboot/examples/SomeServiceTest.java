package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SomeServiceTest {

    @Autowired
    private SomeService someService;

    @Test
    void sync() {
        someService.sync();
    }

    @Test
    void async() {
        someService.async();
    }
}