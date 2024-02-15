package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootExamplesApplicationTests {

    @Test
    void contextLoads() {
        try {
            Integer.parseInt("hello");
        } catch (NumberFormatException _) {
            System.err.println("Error");
        }
    }
}
