package com.example.library.infrastructure.repositories;

import com.example.library.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserById(Long id);

    Optional<UserEntity> findClientByEmail(String email);
}
