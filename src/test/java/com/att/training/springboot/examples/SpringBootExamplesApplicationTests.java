package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootExamplesApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void foo() {
        int x = 2;
        int y = 3;
        var math = STR."\{x} + \{y} = \{x + y}";
    }
}
