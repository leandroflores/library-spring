package com.example.library.infrastructure.configuration;

import com.example.library.core.gateways.UserGateway;
import com.example.library.core.use_cases.users.*;
import com.example.library.core.use_cases.users.implementations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserGateway gateway) {
        return new GetAllUsersUseCaseImplementation(gateway);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserGateway gateway) {
        return new GetUserUseCaseImplementation(gateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway gateway) {
        return new CreateUserUseCaseImplementation(gateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway gateway) {
        return new DeleteUserUseCaseImplementation(gateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway gateway) {
        return new UpdateUserUseCaseImplementation(gateway);
    }
}
