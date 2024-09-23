package com.example.library.infrastructure.controllers.mappers;

import com.example.library.core.entities.User;
import com.example.library.infrastructure.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {

    public User toUser(UserDTO user) {
        return new User(
                null,
                user.name(),
                user.email(),
                user.phone(),
                LocalDate.now()
        );
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
