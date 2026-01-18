package com.att.training.springboot.examples.service;

import com.att.training.springboot.examples.domain.TodoItem;
import com.att.training.springboot.examples.persistence.TodoRepository;
import com.azure.cosmos.models.PartitionKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService(todoRepository);
    }

    @Test
    void createTodo() {
        var item = new TodoItem();
        item.setUserId("user1");
        item.setDescription("Test task");

        when(todoRepository.save(any(TodoItem.class))).thenAnswer(invocation -> {
            TodoItem i = invocation.getArgument(0);
            i.setId("generated-id");
            return i;
        });

        TodoItem created = todoService.createTodo(item);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getUserId()).isEqualTo("user1");
        verify(todoRepository).save(item);
    }

    @Test
    void getTodo() {
        TodoItem item = new TodoItem("id1", "user1", "desc", false);
        when(todoRepository.findById(eq("id1"), any(PartitionKey.class))).thenReturn(Optional.of(item));

        Optional<TodoItem> result = todoService.getTodo("id1", "user1");

        assertThat(result).map(TodoItem::getDescription).contains("desc");
    }
}
