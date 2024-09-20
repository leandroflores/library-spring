package com.example.library.controllers;

import com.example.library.domain.User;
import com.example.library.services.RecommendationService;
import com.example.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable Long user_id) {
        try {
            Optional<User> user = service.getUserById(user_id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/recommend/{user_id}")
    public ResponseEntity<?> recommendBook(@PathVariable Long user_id) {
        try {
            Optional<User> user = service.getUserById(user_id);
            if (user.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(recommendationService.recommendBooks(user.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        try {
            if (!service.checkUserParameters(user)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User email already exists");
            }

            User newUser = service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        try {
            boolean deletedUser = service.deleteUser(user_id);
            if (!deletedUser) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok("User deleted successfully");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<?> updateUser(@PathVariable Long user_id, @Valid @RequestBody User user) {
        try {
            Optional<User> updatedUser = service.updateUser(user_id, user);
            if (updatedUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(updatedUser);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }
}
