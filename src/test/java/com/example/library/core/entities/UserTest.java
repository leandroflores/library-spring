package com.example.library.core.entities;

import com.example.library.configuration.TestUserConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

public class UserTest {

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestUserConfiguration.user();
    }

    @Test
    public void testGetId() throws Exception {
        Long id = TestUserConfiguration.userId();
        assert user.getId().equals(id);
    }

    @Test
    public void testGetName() throws Exception {
        String name = TestUserConfiguration.userName();
        assert user.getName().equals(name);
    }

    @Test
    public void testGetEmail() throws Exception {
        String email = TestUserConfiguration.userEmail();
        assert user.getEmail().equals(email);
    }

    @Test
    public void testGetPhone() throws Exception {
        String phone = TestUserConfiguration.userPhone();
        assert user.getPhone().equals(phone);
    }

    @Test
    public void testGetCreatedAt() throws Exception {
        LocalDate createdAt = TestUserConfiguration.userCreatedAt();
        assert user.getCreatedAt().equals(createdAt);
    }
}
