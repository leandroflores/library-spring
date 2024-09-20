package com.example.library.services;

import com.example.library.TestConfig;
import com.example.library.domain.User;
import com.example.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = TestConfig.user();

        when(repository.save(user)).thenReturn(user);

        User newUser = service.createUser(user);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user.getName(), newUser.getName());
        Assertions.assertEquals(user.getEmail(), newUser.getEmail());
        Assertions.assertEquals(user.getPhone(), newUser.getPhone());
        Assertions.assertEquals(user.getCreatedAt(), newUser.getCreatedAt());

        verify(repository, times(1)).save(user);
    }

    @Test
    public void testCheckEmail() {
        User user = TestConfig.user();

        when(repository.existsByEmail(user.getEmail())).thenReturn(false);

        boolean check = service.checkUserParameters(user);

        Assertions.assertTrue(check);

        verify(repository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    public void testGetFoundUserById() {
        User user = TestConfig.user();

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> result = service.getUserById(user.getId());
        User userFound = result.orElse(null);

        Assertions.assertNotNull(userFound);
        Assertions.assertEquals(user.getName(), userFound.getName());
        Assertions.assertEquals(user.getEmail(), userFound.getEmail());
        Assertions.assertEquals(user.getPhone(), userFound.getPhone());
        Assertions.assertEquals(user.getCreatedAt(), userFound.getCreatedAt());

        verify(repository, times(1)).findById(user.getId());
    }

    @Test
    public void testGetNotFoundUserById() {
        User user = TestConfig.user();

        when(repository.findById(user.getId())).thenReturn(null);

        Optional<User> userNotFound = service.getUserById(user.getId());

        Assertions.assertNull(userNotFound);

        verify(repository, times(1)).findById(user.getId());
    }


}
