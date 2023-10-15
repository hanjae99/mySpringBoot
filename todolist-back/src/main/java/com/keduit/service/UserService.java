package com.keduit.service;

import com.keduit.model.UserEntity;
import com.keduit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if (userEntity == null || userEntity.getUserName() == null){
            throw new RuntimeException("invalid arguments");
        }

        final String userName = userEntity.getUserName();
        if (userRepository.existsByUserName(userName)){
            log.warn("이미 등록된 사용자가 있습니다.", userName);
            throw new RuntimeException("이미 등록된 사용자가 있습니다.");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String userName, final String password,
                                       final PasswordEncoder passwordEncoder){
        final UserEntity oriUser = userRepository.findByUserName(userName);

        // 비밀번호 검증
        if (oriUser != null && passwordEncoder.matches(password, oriUser.getPassword())){
            return oriUser;
        }

        return null;
    }
}
