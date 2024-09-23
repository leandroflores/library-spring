package com.example.library.core.use_cases.users.implementations;

import com.example.library.core.entities.User;
import com.example.library.core.exceptions.BusinessException;
import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.CreateUserUseCase;

public class CreateUserUseCaseImplementation implements CreateUserUseCase {

    private final UserGateway gateway;

    public CreateUserUseCaseImplementation(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(User user) {
        if (gateway.findByEmail(user.getEmail()).isPresent()) {
            String message = "User already exists with this email: " + user.getEmail();
            throw new BusinessException(message);
        }
        return gateway.createUser(user);
    }
}
