package com.att.training.springboot.examples;

import com.azure.messaging.eventhubs.EventData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class EventHubsAppTest extends EventHubsContainer {
    @Autowired
    private MockMvcTester mockMvc;
    @MockitoBean
    private EventProcessor eventProcessor;

    @Test
    void testApp() {
        var response = mockMvc.post()
                .uri("/publish")
                .param("message", "Hello World!");

        assertThat(response).hasStatus2xxSuccessful();
        await().atMost(5, SECONDS).untilAsserted(
                () -> verify(eventProcessor).processEvent(any(EventData.class))
        );
    }
}
