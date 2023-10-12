package com.keduit.service;

import com.keduit.model.TodoEntity;
import com.keduit.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    public String testService() {
        TodoEntity todo = TodoEntity.builder()
                .title("My first todo list")
                .build();
        repository.save(todo);

        // Optional 리턴은 get으로 가져와야함
        TodoEntity savedEntity = repository.findById(todo.getId()).get();

        return savedEntity.getTitle();
    }

    private void validate(final TodoEntity entity) {
        // validate 검사
        // 1. null 확인
        if (entity == null) {
            log.warn("Entity is null@@@@");
            throw new RuntimeException("Entity is null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user@@@@");
            throw  new RuntimeException("Unknown user.");
        }
    }

    // userId 확인
    public List<TodoEntity> create(TodoEntity entity) {
        validate(entity);
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());
        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> read(String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        // 유효한지 확인
        validate(entity);

        // id를 이용해서 Entity 가져오게
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });
        return read(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity todoEntity) {
        validate(todoEntity);

        try{
            repository.delete(todoEntity);
        } catch (Exception e) {
            log.error("delete error", todoEntity.getId(), e);
            throw new RuntimeException("delete error" + todoEntity.getId());
        }

        return repository.findByUserId(todoEntity.getUserId());
    }
}
