package com.att.training.springboot.examples.user;

import com.att.training.springboot.examples.log.LogContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    User getById(@PathVariable int id) {
        LogContext.setOperationName("get-user");
        log.info("#getById - Attempt to fetch user {}", id);
        var user = userService.getById(id);
        log.atInfo()
                .setMessage("#getById - Found user!")
                .addKeyValue("userId", user.id())
                .addKeyValue("username", user.name())
                .log();

        return user;
    }
}

