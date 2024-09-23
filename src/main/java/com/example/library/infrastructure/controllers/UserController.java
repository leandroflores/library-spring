package com.example.library.infrastructure.controllers;

import com.example.library.core.entities.User;
import com.example.library.core.use_cases.users.*;
import com.example.library.infrastructure.controllers.mappers.UserMapper;
import com.example.library.infrastructure.dtos.ResponseDTO;
import com.example.library.infrastructure.dtos.UserDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users/")
@AllArgsConstructor
public class UserController {

    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    private final UserMapper mapper;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return getAllUsersUseCase
                .execute()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        User user = getUserUseCase.execute(userId);
        return mapper.toDTO(user);
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO user) {
        User entity = mapper.toUser(user);
        User newUser = createUserUseCase.execute(entity);
        return mapper.toDTO(newUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseDTO deleteUser(@PathVariable Long userId) {
        boolean deleted = deleteUserUseCase.execute(userId);
        if (deleted) {
            return new ResponseDTO("User deleted successfully");
        }
        return new ResponseDTO("Error deleting User");
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO user) {
        User entity = mapper.toUser(user);
        User updatedUser = updateUserUseCase.execute(userId, entity);
        return mapper.toDTO(updatedUser);
    }
}
