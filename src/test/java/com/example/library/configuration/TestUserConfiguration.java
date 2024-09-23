package com.example.library.configuration;

import com.example.library.core.entities.User;
import com.example.library.infrastructure.dtos.UserDTO;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.UserEntity;

import java.time.LocalDate;

public class TestUserConfiguration {
    private static final UserEntityMapper USER_ENTITY_MAPPER = new UserEntityMapper();

    public static UserEntityMapper entityMapper() {
        return USER_ENTITY_MAPPER;
    }

    public static Long userId() {
        return 1L;
    }

    public static String userName() {
        return "Paul";
    }

    public static String userEmail() {
        return "paul@mail.com";
    }

    public static String userPhone() {
        return "4499999999";
    }

    public static LocalDate userCreatedAt() {
        return LocalDate.now();
    }

    public static User user() {
        return new User(
                userId(),
                userName(),
                userEmail(),
                userPhone(),
                userCreatedAt()
        );
    }

    public static UserEntity userEntity() {
        return entityMapper().toEntity(user());
    }

    public static UserDTO userDTO() {
        return new UserDTO(
                "Paul",
                "paul2@gmail.com",
                "4499999999"
        );
    }
}
