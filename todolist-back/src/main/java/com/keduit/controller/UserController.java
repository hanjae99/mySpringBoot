package com.keduit.controller;

import com.keduit.dto.ResponseDTO;
import com.keduit.dto.UserDTO;
import com.keduit.model.UserEntity;
import com.keduit.security.TokenProvider;
import com.keduit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            if (userDTO == null || userDTO.getPassword() == null){
                throw new RuntimeException("올바른 회원가입 양식이 아닙니다.");
            }

            // 비밀번호 암호화해서 entity 생성
            UserEntity user = UserEntity.builder()
                    .userName(userDTO.getUserName())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            UserEntity registerdUser = userService.create(user);
            UserDTO response = UserDTO.builder()
                    .id(user.getId())
                    .userName(user.getUserName())
                    .build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getUserName(), userDTO.getPassword(), passwordEncoder);

        if (user == null){
            ResponseDTO response = ResponseDTO.builder()
                    .error("해당 유저가 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        final String token = tokenProvider.create(user);
        final UserDTO response = UserDTO.builder()
                .userName(user.getUserName())
                .id(user.getId())
                .token(token)
                .build();
        return ResponseEntity.ok().body(response);
    }
}
