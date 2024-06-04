package com.att.training.springboot.examples;

import io.restassured.RestAssured;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

class FilterTest {
    @Nested
    @SpringBootTest(webEnvironment = RANDOM_PORT)
    @TestInstance(PER_CLASS)
    class SpringBootServerRestAssuredFilterTest {
        @LocalServerPort
        private int port;
        @Value("${server.servlet.context-path}")
        private String contextPath;

        @BeforeAll
        void init() {
            RestAssured.basePath = contextPath;
            RestAssured.port = port;
        }

        @Test
        void greetingFilter_shouldAddGreetingInRequestAttribute() {
            given()
                    .when().get(HelloController.GREET_PATH)
                    .then().assertThat().statusCode(equalTo(200))
                    .and().assertThat().body(equalTo(GreetingFilter.GREETING_VALUE));
        }
    }

    @RestController
    static class HelloController {
        static final String GREET_PATH = "/greet";

        @GetMapping(GREET_PATH)
        public String greet(@RequestAttribute String greeting) {
            return greeting;
        }
    }

    @Component
    static class GreetingFilter extends OncePerRequestFilter {
        private static final String GREETING_KEY = "greeting";
        static final String GREETING_VALUE = "Hello!";

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws IOException, ServletException {
            request.setAttribute(GREETING_KEY, GREETING_VALUE);
            filterChain.doFilter(request, response);
        }
    }
}
