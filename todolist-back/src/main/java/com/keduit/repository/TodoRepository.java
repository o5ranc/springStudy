package com.keduit.repository;

import com.keduit.dto.TodoDTO;
import com.keduit.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userId);

    @Query("SELECT t from TodoEntity t WHERE t.userId = ?1")
    TodoEntity findUserIdQuery(String userId);
}
