package com.att.training.springboot.examples;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.persistence.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AzureCosmosDbApplicationTests {
    @Container
    @ServiceConnection
    private static final CosmosDBEmulatorContainer cosmos = new CosmosDBEmulatorContainer(
            DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:vnext-preview")
    ).withCommand("--protocol", "https");
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvcTester mockMvcTester;
    @Autowired
    private TodoRepository todoRepository;

    static {
        cosmos.start();
        configureTrustStore();
    }

    private static void configureTrustStore() {
        System.setProperty("javax.net.ssl.trustStore", "src/test/resources/azure-cosmos-emulator-truststore.p12");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
    }

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    void testCreateAndGetTodo() throws JsonProcessingException {
        String userId = "user-" + UUID.randomUUID();
        var item = new TodoItem();
        item.setUserId(userId);
        item.setDescription("Component Test Task");

        TodoItem[] createdItemHolder = new TodoItem[1];

        mockMvcTester.post()
                .uri("/api/todos")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(item))
                .assertThat()
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(TodoItem.class)
                .satisfies(createdItem -> {
                    assertThat(createdItem).isNotNull()
                            .extracting(TodoItem::getId).isNotNull();
                    createdItemHolder[0] = createdItem;
                });


        mockMvcTester.get()
                .uri("/api/todos/{userId}/{id}", userId, createdItemHolder[0].getId())
                .accept(APPLICATION_JSON)
                .assertThat()
                .hasStatusOk()
                .bodyJson()
                .convertTo(TodoItem.class)
                .isNotNull()
                .extracting(TodoItem::getDescription)
                .isEqualTo("Component Test Task");
    }
}
