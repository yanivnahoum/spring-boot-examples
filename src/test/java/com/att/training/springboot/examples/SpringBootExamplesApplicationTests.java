package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootExamplesApplicationTests {
    @Autowired
    private SomeService someService;

    @Test
    void derived1() {
        assertThat(someService.getDerived1Name()).isEqualTo(Derived1.class.getSimpleName());
    }

    @Test
    void derived2() {
        assertThat(someService.getDerived2Name()).isEqualTo(Derived2.class.getSimpleName());
    }
}
