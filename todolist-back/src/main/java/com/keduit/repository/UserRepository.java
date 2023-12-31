package com.keduit.repository;

import com.keduit.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserName(String userName);

    Boolean existsByUserName(String userName);

    UserEntity findByUserNameAndPassword(String userName, String password);
}
