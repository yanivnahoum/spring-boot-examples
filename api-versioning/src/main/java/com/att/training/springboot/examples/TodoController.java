package com.att.training.springboot.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "{version}/todos")
@Slf4j
public class TodoController {
    @GetMapping(version = "v1")
    public List<Todo> getTodos() {
        log.info("Getting todos v1");
        return List.of(new Todo(1L, "Learn Spring Boot 4 - v1", false));
    }


    @GetMapping(version = "v2")
    public List<Todo> getTodosV2() {
        log.info("Getting todos v2");
        return List.of(new Todo(1L, "Learn Spring Boot 4 - v2", false));
    }
}
