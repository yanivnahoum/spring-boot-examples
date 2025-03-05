package com.att.training.springboot.examples;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class UserRestClientTest {
    private MockWebServer mockWebServer;
    private UserRestClient userClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        UserClientProperties userClientProperties = new UserClientProperties(
                mockWebServer.url("/api").toString(),
                Duration.ofSeconds(1),
                Duration.ofSeconds(1)
        );
        userClient = new UserRestClient(RestClient.builder(), userClientProperties);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

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

        var user = userClient.get(1);

        assertThat(user).isEqualTo(new User(1, "John"));
    }
}
