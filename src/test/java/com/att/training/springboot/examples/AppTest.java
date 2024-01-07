package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppTest {
    @Autowired
    private AppProperties appProperties;

    @Test
    void contextLoads() {
        var expectedFullName = new FullName("John", "Doe");
        assertThat(appProperties.name()).isEqualTo(expectedFullName);
    }
}

