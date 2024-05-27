package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class SpringBootExamplesApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(5));
    }
}
