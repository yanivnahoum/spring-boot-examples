package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration(proxyBeanMethods = false)
public class RouteConfig {
    @Bean
    RouterFunction<ServerResponse> routes(Handlers handlers) {
        return route()
                .GET("/hello", request -> ok().body("Hello, world!"))
                .GET("/validate/{number}", handlers::validateNumber)
                .onError(Exception.class, Handlers::handleException)
                .build();
    }
}

@Component
@RequiredArgsConstructor
class Handlers {
    public ServerResponse validateNumber(ServerRequest request) {
        int number = Integer.parseInt(request.pathVariable("number"));
        if (number < 1) {
            throw new InvalidIdException("Negative number");
        }
        return ok().body("Valid");
    }

    public static ServerResponse handleException(Throwable throwable, ServerRequest serverRequest) {
        return switch (throwable) {
            case InvalidIdException iie -> ServerResponse.badRequest().body("Invalid: " + iie.getMessage());
            case ResponseStatusException rse -> ServerResponse.status(rse.getStatusCode()).body(rse.getReason());
            default -> ServerResponse.status(INTERNAL_SERVER_ERROR).body("Generic");
        };
    }

    public static ServerResponse handleException(ServerRequest serverRequest, HandlerFunction<ServerResponse> next) {
        try {
            return next.handle(serverRequest);
        } catch (Exception e) {
            return toServerResponse(e);
        }
    }

    private static ServerResponse toServerResponse(Exception e) {
        return switch (e) {
            case InvalidIdException iie -> ServerResponse.badRequest().body("Invalid: " + iie.getMessage());
            case ResponseStatusException rse -> ServerResponse.status(rse.getStatusCode()).body(rse.getReason());
            default -> ServerResponse.status(INTERNAL_SERVER_ERROR).body("Generic");
        };
    }
}

@StandardException
class InvalidIdException extends RuntimeException {}
