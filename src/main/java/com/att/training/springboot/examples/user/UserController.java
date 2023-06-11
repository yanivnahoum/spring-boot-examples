package com.att.training.springboot.examples.user;

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
        return userService.getById(id);
    }

    @GetMapping("{id}")
    UserPair getPairById(@PathVariable int id) {
        return userService.getPairById(id, id + 1);
    }
}

