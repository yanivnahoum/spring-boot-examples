package com.att.training.springboot.examples;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class UserRestClientTest {
    private MockWebServer mockWebServer;
    private UserRestClient userRestClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        UserClientProperties userClientProperties = new UserClientProperties(mockWebServer.url("/api").toString());
        userRestClient = new UserRestClient(RestClient.builder(), userClientProperties);
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

        var user = userRestClient.getUser(1);

        assertThat(user).isEqualTo(new User(1, "John"));
    }
}
