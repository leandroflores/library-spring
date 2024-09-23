package com.example.library.core.use_cases.users.implementations;

import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.DeleteUserUseCase;

public class DeleteUserUseCaseImplementation implements DeleteUserUseCase {

    private final UserGateway gateway;

    public DeleteUserUseCaseImplementation(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean execute(Long id) {
        boolean deleted = gateway.deleteUser(id);
        if (!deleted) {
            throw new EntityNotFoundException("User", "Id", id);
        }
        return true;
    }
}
