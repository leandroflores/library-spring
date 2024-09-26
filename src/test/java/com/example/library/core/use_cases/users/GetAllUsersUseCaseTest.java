package com.example.library.core.use_cases.users;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.core.use_cases.users.implementations.GetAllUsersUseCaseImplementation;
import com.example.library.infrastructure.gateways.UserRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class GetAllUsersUseCaseTest {

    @Autowired
    private UserRepository repository;

    @Mock
    private UserEntityMapper mapper;

    @Mock
    private UserRepositoryGateway gateway;

    @Mock
    private GetAllUsersUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
        gateway = new UserRepositoryGateway(repository, mapper);
        useCase = new GetAllUsersUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        UserEntity user = repository.save(mapper.toEntity(TestUserConfiguration.user()));

        List<User> users = useCase.execute();

        assert users.size() == 1;
        assert users.get(0).getId().equals(user.getId());
        assert users.get(0).getEmail().equals(user.getEmail());
        assert users.get(0).getName().equals(user.getName());
        assert users.get(0).getPhone().equals(user.getPhone());
        assert users.get(0).getCreatedAt().equals(user.getCreatedAt());
    }
}
