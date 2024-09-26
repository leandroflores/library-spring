package com.example.library.core.use_cases.users;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.core.use_cases.users.implementations.UpdateUserUseCaseImplementation;
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
public class UpdateUserUseCaseTest {

    @Autowired
    private UserRepository repository;

    @Mock
    private UserEntityMapper mapper;

    @Mock
    private UserRepositoryGateway gateway;

    @Mock
    private UpdateUserUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
        gateway = new UserRepositoryGateway(repository, mapper);
        useCase = new UpdateUserUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        User user = TestUserConfiguration.user();
             user.setEmail("another@mail.com");
             user.setName("Another Name");

        UserEntity userSaved = repository.save(mapper.toEntity(user));

        User userFound = useCase.execute(userSaved.getId(), user);

        assert userFound.getId().equals(user.getId());
        assert userFound.getEmail().equals(user.getEmail());
        assert userFound.getName().equals(user.getName());
        assert userFound.getPhone().equals(user.getPhone());
        assert userFound.getCreatedAt().equals(user.getCreatedAt());
    }
}
