package com.att.training.springboot.examples.oracle;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;

    @GetMapping
    public List<User> findAll() {
        return userDao.findAll();
    }
}
