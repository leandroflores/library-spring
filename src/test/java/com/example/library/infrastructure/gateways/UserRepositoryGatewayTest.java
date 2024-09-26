package com.example.library.infrastructure.gateways;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryGatewayTest {

    @Autowired
    private UserRepository repository;

    @Mock
    private UserEntityMapper mapper;

    @Mock
    private UserRepositoryGateway gateway;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
        gateway = new UserRepositoryGateway(repository, mapper);
    }

    @Test
    public void testCreateUser() {
        User user = TestUserConfiguration.user();

        User newUser = gateway.createUser(user);

        assert newUser.getEmail().equals(user.getEmail());
        assert newUser.getName().equals(user.getName());
        assert newUser.getPhone().equals(user.getPhone());
        assert newUser.getCreatedAt().equals(user.getCreatedAt());
    }

    @Test
    public void testUpdateUser() {
        User user = TestUserConfiguration.user();
             user.setName("wfedasç2");
        User updatedUser = gateway.saveUser(user);

        assert updatedUser.getEmail().equals(user.getEmail());
        assert updatedUser.getName().equals(user.getName());
        assert updatedUser.getPhone().equals(user.getPhone());
        assert updatedUser.getCreatedAt().equals(user.getCreatedAt());
    }

    @Test
    public void testDeleteUser() {
        User user = gateway.createUser(TestUserConfiguration.user());

        gateway.deleteUser(user.getId());

        assert gateway.getUser(user.getId()).isEmpty();
    }

    @Test
    public void testGetUser() {
        User user = gateway.createUser(TestUserConfiguration.user());

        Optional<User> userFound = gateway.getUser(user.getId());

        assert userFound.isPresent();
        assert userFound.get().getEmail().equals(user.getEmail());
        assert userFound.get().getName().equals(user.getName());
        assert userFound.get().getPhone().equals(user.getPhone());
        assert userFound.get().getCreatedAt().equals(user.getCreatedAt());
    }

    @Test
    public void testFindByEmail() {
        String mail = "user@mail.com.br";
        User user = TestUserConfiguration.user();
             user.setEmail(mail);

        gateway.createUser(user);
        Optional<User> userFound = gateway.findByEmail(mail);

        assert userFound.isPresent();
        assert userFound.get().getEmail().equals(user.getEmail());
        assert userFound.get().getName().equals(user.getName());
        assert userFound.get().getPhone().equals(user.getPhone());
        assert userFound.get().getCreatedAt().equals(user.getCreatedAt());
    }

    @Test
    public void testGetAllUsers() {
        User user = gateway.createUser(TestUserConfiguration.user());

        List<User> users = gateway.getAllUsers();

        assert users.size() == 1;
        assert users.get(0).getEmail().equals(user.getEmail());
        assert users.get(0).getName().equals(user.getName());
        assert users.get(0).getPhone().equals(user.getPhone());
        assert users.get(0).getCreatedAt().equals(user.getCreatedAt());
    }
}
