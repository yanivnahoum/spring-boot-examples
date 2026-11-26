package com.att.training.springboot.examples;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.persistence.TodoRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.json.JsonMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AzureCosmosDbComponentTest {
    @Container
    @ServiceConnection
    private static final CosmosDBEmulatorContainer cosmos = new CosmosDBEmulatorContainer(
            DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:vnext-latest")
    ).withCommand("--protocol", "https");
    @TempDir
    private static Path tempFolder;
    @Autowired
    private JsonMapper mapper;
    @Autowired
    private MockMvcTester mockMvcTester;
    @Autowired
    private TodoRepository todoRepository;

    @BeforeAll
    static void beforeAll() {
        configureTrustStore();
    }

    @SneakyThrows({ IOException.class, GeneralSecurityException.class })
    private static void configureTrustStore() {
        Path keyStoreFile = tempFolder.resolve("azure-cosmos-emulator.p12");
        KeyStore keyStore = cosmos.buildNewKeyStore();
        String keyStorePassword = cosmos.getEmulatorKey();
        keyStore.store(new FileOutputStream(keyStoreFile.toFile()), keyStorePassword.toCharArray());
        System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);
    }

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    void testCreateAndGetTodo() {
        String userId = "user-" + UUID.randomUUID();
        var item = new TodoItem();
        item.setUserId(userId);
        item.setDescription("Component Test Task");

        TodoItem createdItem = mockMvcTester.post()
                .uri("/api/todos")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(item))
                .assertThat()
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(TodoItem.class)
                .satisfies(todoItem -> assertThat(todoItem).isNotNull()
                        .extracting(TodoItem::getId)
                        .isNotNull())
                .actual();


        mockMvcTester.get()
                .uri("/api/todos/{userId}/{id}", userId, createdItem.getId())
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
