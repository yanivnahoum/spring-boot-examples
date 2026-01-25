package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.MockMvcBuilderCustomizer;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.web.client.ApiVersionInserter;

@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void shouldReturnTodosForExactVersion() {
        mvc.get().uri("/todos")
                .apiVersion("v1")
                .assertThat()
                .hasStatusOk()
                .bodyText().contains("Learn Spring Boot 4 - v1");
    }

    @Test
    void shouldReturnTodosForNewerMinorVersion() {
        // Show-casing "greater than x" matching
        mvc.get().uri("/todos")
                .apiVersion("v2")
                .assertThat()
                .hasStatusOk()
                .bodyText().contains("Learn Spring Boot 4 - v2");
    }

    @Test
    void shouldFailForUnknownVersion() {
        mvc.get().uri("/todos")
                .apiVersion("v3")
                .assertThat()
                .hasStatus4xxClientError();
    }

    @TestConfiguration
    static class TestConfig implements MockMvcBuilderCustomizer {
        @Override
        public void customize(ConfigurableMockMvcBuilder<?> builder) {
            var versionInserter = ApiVersionInserter.usePathSegment(0);
            builder.apiVersionInserter(versionInserter)
                    .alwaysDo(MockMvcResultHandlers.print());
        }
    }
}
