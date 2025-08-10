package com.att.training.springboot.examples;

import io.micrometer.observation.ObservationRegistry;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class UserRestClientTest {
    @AutoClose
    private MockWebServer mockWebServer;
    private UserRestClient userClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        UserClientProperties userClientProperties = new UserClientProperties(
                mockWebServer.url("/api").toString(),
                Duration.ofSeconds(1),
                Duration.ofSeconds(1),
                5,
                25
        );
        userClient = new UserRestClient(RestClient.builder(), userClientProperties, ObservationRegistry.NOOP);
    }

    @Test
    void givenUserJohn_whenGetUser_thenReturnJohn() {
        mockWebServer.enqueue(new MockResponse.Builder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body("""
                        {
                            "id": 1,
                            "name": "John"
                        }
                        """)
                .build()
        );

        var user = userClient.get(1);

        assertThat(user).isEqualTo(new User(1, "John"));
    }
}
