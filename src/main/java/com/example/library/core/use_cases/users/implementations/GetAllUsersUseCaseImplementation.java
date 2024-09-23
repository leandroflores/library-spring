package com.example.library.core.use_cases.users.implementations;

import com.example.library.core.entities.User;
import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.GetAllUsersUseCase;

import java.util.List;

public class GetAllUsersUseCaseImplementation implements GetAllUsersUseCase {

    private final UserGateway gateway;

    public GetAllUsersUseCaseImplementation(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<User> execute() {
        return gateway.getAllUsers();
    }
}
