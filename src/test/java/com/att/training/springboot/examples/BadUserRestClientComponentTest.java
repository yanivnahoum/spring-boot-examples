package com.att.training.springboot.examples;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BadUserRestClientComponentTest {
    private static MockWebServer mockWebServer;
    @Autowired
    private UserRestClient userRestClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void addProperties(DynamicPropertyRegistry registry) {
        registry.add("app.user.base-url", () -> mockWebServer.url("/api").toString());
    }

    @Order(1)
    @Test
    @SuppressWarnings("java:S2699")
    void givenUserJane_leaveResponseQueued() {
        mockWebServer.enqueue(new MockResponse()
                .setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                            "id": 2,
                            "name": "Jane"
                        }
                        """)
        );

        // We then run some code that throws an exception or fails to dequeue the response
    }

    @Order(2)
    @Test
    void givenUserJohn_whenGetUser_thenReturnJohn() {
        mockWebServer.enqueue(new MockResponse()
                .setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                            "id": 1,
                            "name": "John"
                        }
                        """)
        );

        var user = userRestClient.getUser(1);

        assertThat(user).isEqualTo(new User(1, "John"));
    }
}