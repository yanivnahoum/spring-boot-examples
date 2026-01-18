package com.att.training.springboot.examples.service;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.persistence.TodoRepository;
import com.azure.cosmos.models.PartitionKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoItem createTodo(TodoItem item) {
        log.info("Creating todo for user: {}", item.getUserId());
        return todoRepository.save(item);
    }

    public Optional<TodoItem> getTodo(String id, String userId) {
        return todoRepository.findById(id, new PartitionKey(userId));
    }

    public List<TodoItem> getTodosByUser(String userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<TodoItem> getTodosByUserAndStatus(String userId, boolean completed) {
        return todoRepository.findByUserIdAndCompleted(userId, completed);
    }

    public TodoItem updateTodo(String id, String userId, TodoItem updatedItem) {
        return todoRepository.findById(id, new PartitionKey(userId))
                .map(existing -> {
                    existing.setDescription(updatedItem.getDescription());
                    existing.setCompleted(updatedItem.isCompleted());
                    return todoRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    public void deleteTodo(String id, String userId) {
        log.info("Deleting todo: {} for user: {}", id, userId);
        todoRepository.deleteById(id, new PartitionKey(userId));
    }
}
