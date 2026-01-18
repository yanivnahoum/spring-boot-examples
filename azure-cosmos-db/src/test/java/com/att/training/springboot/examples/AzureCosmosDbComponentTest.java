package com.att.training.springboot.examples;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.persistence.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
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
            DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:vnext-preview")
    ).withCommand("--protocol", "https");
    @TempDir
    private static Path tempFolder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvcTester mockMvcTester;
    @Autowired
    private TodoRepository todoRepository;

    @BeforeAll
    static void beforeAll() {
        configureTrustStore();
    }

    @SneakyThrows({IOException.class, GeneralSecurityException.class})
    private static void configureTrustStore() {
        String keyStorePassword = cosmos.getEmulatorKey();
        KeyStore keyStore = buildKeyStore(keyStorePassword);
        Path keyStoreFile = tempFolder.resolve("azure-cosmos-emulator.p12");
        keyStore.store(new FileOutputStream(keyStoreFile.toFile()), keyStorePassword.toCharArray());
        System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);
    }

    @SneakyThrows({IOException.class, InterruptedException.class, GeneralSecurityException.class})
    private static KeyStore buildKeyStore(String keyStorePassword) {
        ExecResult execResult = cosmos.execInContainer("sh", "-c",
                "openssl s_client -connect localhost:8081 </dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p'");
        InputStream certificateStream = new ByteArrayInputStream(execResult.getStdout().getBytes());
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(certificateStream);
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(null, keyStorePassword.toCharArray());
        keystore.setCertificateEntry("azure-cosmos-emulator", certificate);
        return keystore;
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
