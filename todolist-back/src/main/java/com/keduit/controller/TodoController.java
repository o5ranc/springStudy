package com.keduit.controller;

import com.keduit.dto.ResponseDTO;
import com.keduit.dto.TodoDTO;
import com.keduit.model.TodoEntity;
import com.keduit.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController // 알아서 JSON으로 변환
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping(value = "/test")
    public ResponseEntity<?> testTodo() {
        String str = todoService.testService();
        List<String> list = new ArrayList<>();

        list.add(str);
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder()
                .data(list).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity selectTodo(@AuthenticationPrincipal String userId) {
        try{
            List<TodoEntity> read = todoService.read(userId);

            List<TodoDTO> collect = read.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> build = ResponseDTO.<TodoDTO>builder()
                    .data(collect)
                    .build();

            return ResponseEntity.ok().body(build);

        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO build = ResponseDTO.builder()
                    .error(error)
                    .build();

            return ResponseEntity.badRequest().body(build);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId) {
        try {
            //String tempUserId = "temporal-user";

            TodoEntity entity = TodoDTO.toEntity(todoDTO);
            entity.setId(null);
            //entity.setUserId(tempUserId);
            entity.setUserId(userId); // tempUserId 대신 userId 정보 가져옴
            
            // 서비스로부터 엔티티 List를 가져옴
            List<TodoEntity> entities = todoService.create(entity);
            
            // 엔티티 List를 dto리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            
            // 변환된 dto List를 ResponseDTO로 값 설정
            ResponseDTO<TodoDTO> build = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // ResponseDTO Return
            return ResponseEntity.ok().body(build);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId) {

        TodoEntity entity = TodoDTO.toEntity(todoDTO);
        entity.setUserId(userId);

        List<TodoEntity> entities = todoService.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId) {
        try{
            TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
            todoEntity.setUserId(userId);

            List<TodoEntity> todoEntities = todoService.delete(todoEntity);
            List<TodoDTO> dtos = todoEntities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

}
