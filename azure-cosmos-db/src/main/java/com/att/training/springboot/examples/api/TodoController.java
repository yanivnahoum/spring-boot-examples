package com.att.training.springboot.examples.api;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoItem> create(@RequestBody TodoItem item) {
        if (item.getUserId() == null || item.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TodoItem created = todoService.createTodo(item);
        return ResponseEntity.created(URI.create("/api/todos/" + created.getUserId() + "/" + created.getId()))
                .body(created);
    }

    @GetMapping("/{userId}/{id}")
    public ResponseEntity<TodoItem> get(@PathVariable String userId, @PathVariable String id) {
        return todoService.getTodo(id, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<TodoItem> list(@RequestParam String userId, @RequestParam(required = false) Boolean completed) {
        if (completed != null) {
            return todoService.getTodosByUserAndStatus(userId, completed);
        }
        return todoService.getTodosByUser(userId);
    }

    @PutMapping("/{userId}/{id}")
    public ResponseEntity<TodoItem> update(@PathVariable String userId, @PathVariable String id, @RequestBody TodoItem item) {
        try {
            return ResponseEntity.ok(todoService.updateTodo(id, userId, item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<Void> delete(@PathVariable String userId, @PathVariable String id) {
        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }
}
