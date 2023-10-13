package com.keduit.controller;

import com.keduit.dto.ResponseDTO;
import com.keduit.dto.UserDTO;
import com.keduit.model.UserEntity;
import com.keduit.security.TokenProvider;
import com.keduit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvidor;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("사용자 혹은 비밀번호가 없습니다.");
            }

            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 encode 기능 추가
                    .build();

            UserEntity registerUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> athunticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(
                userDTO.getUsername(), userDTO.getPassword(),
                passwordEncoder
                );

        if(user != null) { // user가 존재
            final String token = tokenProvidor.create(user);

            final UserDTO resposeUserDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .token(token)
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(resposeUserDTO);
        } else {
            return ResponseEntity.badRequest().body("로그인 에러");
        }


    }
}
