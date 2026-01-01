package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ServiceBusAppTest extends ServiceBusContainer {
    @Autowired
    private MockMvcTester mockMvc;

    @Test
    void testApp() {
        var response = mockMvc.post()
                .uri("/publish")
                .param("message", "Hello World!");

        assertThat(response).hasStatus2xxSuccessful();
    }
}
