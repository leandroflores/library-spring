package com.example.library.infrastructure.persistence;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

public class UserEntityTest {

    @Mock
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = TestUserConfiguration.userEntity();
    }

    @Test
    public void testGetId() {
        Long id = TestUserConfiguration.userId();
        assert user.getId().equals(id);
    }

    @Test
    public void testGetName() {
        String name = TestUserConfiguration.userName();
        assert user.getName().equals(name);
    }

    @Test
    public void testGetEmail() {
        String email = TestUserConfiguration.userEmail();
        assert user.getEmail().equals(email);
    }

    @Test
    public void testGetPhone() {
        String phone = TestUserConfiguration.userPhone();
        assert user.getPhone().equals(phone);
    }

    @Test
    public void testGetCreatedAt() {
        LocalDate createdAt = TestUserConfiguration.userCreatedAt();
        assert user.getCreatedAt().equals(createdAt);
    }
}
