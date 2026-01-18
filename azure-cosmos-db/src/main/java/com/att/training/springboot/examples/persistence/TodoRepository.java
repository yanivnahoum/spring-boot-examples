package com.att.training.springboot.examples.persistence;

import com.att.training.springboot.examples.domain.TodoItem;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CosmosRepository<TodoItem, String> {
    List<TodoItem> findByUserId(String userId);

    List<TodoItem> findByUserIdAndCompleted(String userId, boolean completed);
}
