package com.keduit.controller;

import com.keduit.dto.ResponseDTO;
import com.keduit.dto.TodoDTO;
import com.keduit.model.TodoEntity;
import com.keduit.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    // @RestController 붙어서 ResposeBody 필요없이 ResponseEntity만 적으면 됨
    @GetMapping(value = "/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();

        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .data(list)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String tempUserId = "temporal-user";

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null); // uuid에서 뽑아올 예정이라?
            entity.setUserId(tempUserId);

            List<TodoEntity> entities = service.create(entity);

            // 서비스로부터 entity List 타입을 가져옴
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // Entity List를 TodoDTO List를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // ResponseDTO를 반환
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 예외 발생 시 DTO 대신 error에 메시지를 넣어서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                            .error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String tempUserId = "temporal-user";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(tempUserId);

        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();
        // ResponseDTO를 반환
        return ResponseEntity.ok().body(response);
    }
}