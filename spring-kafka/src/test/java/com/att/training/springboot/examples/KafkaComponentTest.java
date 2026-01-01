package com.att.training.springboot.examples;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.ACCEPTED;

@SpringBootTest(properties = "spring.kafka.consumer.auto-offset-reset=earliest")
@WithKafka
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
        await().atMost(3, SECONDS).untilAsserted(() ->
                verify(recordProcessor).process(payload, groupId)
        );
    }
}
