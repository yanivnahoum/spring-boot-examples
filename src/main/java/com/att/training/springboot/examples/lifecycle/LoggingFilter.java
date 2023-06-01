package com.att.training.springboot.examples.lifecycle;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.atInfo()
                .setMessage(">>> Incoming: {}")
                .addArgument(() -> buildRequestMessage(request));

        filterChain.doFilter(request, response);

        log.atInfo()
                .setMessage(">>> Outgoing: {}")
                .addArgument(() -> buildResponseMessage(request));
    }

    // ...

    private String buildResponseMessage(HttpServletRequest request) {
        return null;
    }

    private String buildRequestMessage(HttpServletRequest request) {
        return null;
    }
}

