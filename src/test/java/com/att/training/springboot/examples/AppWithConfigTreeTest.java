package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.import=configtree:tree/")
class AppWithConfigTreeTest {
    @Autowired
    private AppProperties appProperties;

    @Test
    void contextLoads() {
        var expectedFullName = new FullName("Michael", "Jackson");
        assertThat(appProperties.name()).isEqualTo(expectedFullName);
    }
}
