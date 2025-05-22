package com.att.training.springboot.examples;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.ACCEPTED;

@SpringBootTest
@Import(ContainersConfiguration.class)
@AutoConfigureMockMvc
class KafkaComponentTest {
    @Autowired
    private MockMvcTester mockMvc;
    @MockitoBean
    private RecordProcessor recordProcessor;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @ParameterizedTest
    @ValueSource(strings = {"123", "456", "789"})
    void givenPayload_whenPutProduce_then202AcceptedAndRecordIsProcessed(String payload) {
        var response = mockMvc.put()
                .uri("/produce/{payload}", payload)
                .exchange();
        assertThat(response).hasStatus(ACCEPTED);
        await().atMost(Duration.ofSeconds(3)).untilAsserted(() ->
                verify(recordProcessor).process(payload, groupId)
        );
    }
}
