package com.example.library.core.gateways;

import com.example.library.core.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    User createUser(User user);

    Optional<User> getUser(Long id);

    User saveUser(User user);

    boolean deleteUser(Long id);

    Optional<User> findByEmail(String email);

    List<User> getAllUsers();
}
