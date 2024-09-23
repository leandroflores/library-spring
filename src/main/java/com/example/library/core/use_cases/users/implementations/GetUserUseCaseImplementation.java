package com.example.library.core.use_cases.users.implementations;

import com.example.library.core.entities.User;
import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.GetUserUseCase;

import java.util.Optional;

public class GetUserUseCaseImplementation implements GetUserUseCase {

    private final UserGateway gateway;

    public GetUserUseCaseImplementation(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(Long id) {
        Optional<User> client = gateway.getUser(id);
        if (client.isEmpty()) {
            throw new EntityNotFoundException("User", "Id", id);
        }
        return client.get();
    }
}
