package com.keduit.service;

import com.keduit.model.UserEntity;
import com.keduit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("invalid arguments");
        }

        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)) {
            log.warn("이미 등록된 사용자가 있습니다.", username);
            throw new RuntimeException("이미 등록된 사용자가 있습니다.");
        }

        return userRepository.save(userEntity); // 저장된 Entity를 리턴해 줌
    }

    public UserEntity getByCredentials(
            final String username,
            final String password,
            final PasswordEncoder encoder ) {
        final UserEntity oriUser = userRepository.findByUsernameAndPassword(username, password);

        if(oriUser != null && encoder.matches(password, oriUser.getPassword())) {
            return oriUser;
        }

        return null;
    }
}
