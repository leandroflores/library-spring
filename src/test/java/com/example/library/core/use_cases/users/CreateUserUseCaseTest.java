package com.example.library.core.use_cases.users;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.core.use_cases.users.implementations.CreateUserUseCaseImplementation;
import com.example.library.infrastructure.gateways.UserRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CreateUserUseCaseTest {

    @Autowired
    private UserRepository repository;

    @Mock
    private UserEntityMapper mapper;

    @Mock
    private UserRepositoryGateway gateway;

    @Mock
    private CreateUserUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
        gateway = new UserRepositoryGateway(repository, mapper);
        useCase = new CreateUserUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        User user = TestUserConfiguration.user();

        User newUser = useCase.execute(user);

        assert newUser != null;
        assert newUser.getEmail().equals(user.getEmail());
        assert newUser.getName().equals(user.getName());
        assert newUser.getPhone().equals(user.getPhone());
        assert newUser.getCreatedAt().equals(user.getCreatedAt());
    }
}
