package com.example.library.core.use_cases.users;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.core.use_cases.users.implementations.GetUserUseCaseImplementation;
import com.example.library.infrastructure.gateways.UserRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GetUserUseCaseTest {

    @Autowired
    private UserRepository repository;

    @Mock
    private UserEntityMapper mapper;

    @Mock
    private UserRepositoryGateway gateway;

    @Mock
    private GetUserUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
        gateway = new UserRepositoryGateway(repository, mapper);
        useCase = new GetUserUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        UserEntity user = repository.save(mapper.toEntity(TestUserConfiguration.user()));

        User userFound = useCase.execute(user.getId());

        assert userFound.getId().equals(user.getId());
        assert userFound.getEmail().equals(user.getEmail());
        assert userFound.getName().equals(user.getName());
        assert userFound.getPhone().equals(user.getPhone());
        assert userFound.getCreatedAt().equals(user.getCreatedAt());
    }
}
