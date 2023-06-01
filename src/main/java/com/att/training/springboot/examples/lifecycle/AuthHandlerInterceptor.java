package com.att.training.springboot.examples.lifecycle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean authorized = isAuthorized();

        if (!authorized) {
            response.sendError(UNAUTHORIZED.value(), "Invalid token!");
            return false;
        }
        return true;
    }

    private static boolean isAuthorized() {
        return true;
    }
}

