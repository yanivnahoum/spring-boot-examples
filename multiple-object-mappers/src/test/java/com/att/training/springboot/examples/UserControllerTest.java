package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.json.JsonMapper;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String JSON_WITH_UNKNOWN_PROPERTY = """
            {
                "id": 1,
                "name": "John",
                "age": 30
            }
            """;
    private static final String STANDARD_JSON = """
            {
                "id": 1,
                "name": "John"
            }
            """;
    @Autowired
    private JsonMapper defaultObjectMapper;
    @Strict
    @Autowired
    private JsonMapper strictObjectMapper;
    @Autowired
    private MockMvcTester mockMvc;

    @Test
    void givenUserWithUnknownProperty_whenDeserializingUsingDefaultMapper_thenSucceed() throws JacksonException {
        var user = defaultObjectMapper.readValue(JSON_WITH_UNKNOWN_PROPERTY, User.class);
        assertThat(user).isEqualTo(new User(1, "John"));
    }

    @Test
    void givenUserWithUnknownProperty_whenDeserializingUsingStrictMapper_thenFailWithJsonMappingException() {
        assertThatThrownBy(() -> strictObjectMapper.readValue(JSON_WITH_UNKNOWN_PROPERTY, User.class))
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Unrecognized property \"age\"");
    }

    @ParameterizedTest
    @MethodSource
    void patchRequest(String json, HttpStatus expectedStatus) {
        var result = mockMvc.patch()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .content(json);

        assertThat(result).hasStatus(expectedStatus);
    }

    static Stream<Arguments> patchRequest() {
        return Stream.of(
                argumentSet("given a user, when PATCH Request, then respond with 204",
                        STANDARD_JSON, NO_CONTENT),
                argumentSet("given a user with an unknown property, when PATCH Request, then respond with 204",
                        JSON_WITH_UNKNOWN_PROPERTY, NO_CONTENT)
        );
    }
}
