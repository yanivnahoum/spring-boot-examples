package com.att.training.springboot.examples;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchUser(@Valid @RequestBody User user) {
        log.info("Patching user: {}", user);
    }
}
