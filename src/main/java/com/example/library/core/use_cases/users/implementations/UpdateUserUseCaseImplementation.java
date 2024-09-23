package com.example.library.core.use_cases.users.implementations;

import com.example.library.core.entities.User;
import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.UpdateUserUseCase;

import java.util.Optional;

public class UpdateUserUseCaseImplementation implements UpdateUserUseCase {

    private final UserGateway gateway;

    public UpdateUserUseCaseImplementation(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(Long id, User user) {
        Optional<User> userFound = gateway.getUser(id);
        if (userFound.isEmpty()) {
            throw new EntityNotFoundException("User", "Id", id);
        }
        userFound.get().setName(user.getName());
        userFound.get().setEmail(user.getEmail());
        userFound.get().setPhone(user.getPhone());
        return gateway.saveUser(userFound.get());
    }
}
