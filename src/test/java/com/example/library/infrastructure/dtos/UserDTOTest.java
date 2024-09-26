package com.example.library.infrastructure.dtos;

import com.example.library.configuration.TestUserConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserDTOTest {

    @Mock
    private UserDTO user;

    @BeforeEach
    public void setUp() {
        user = TestUserConfiguration.userDTO();
    }

    @Test
    public void testName() {
        assert user.name().equals(TestUserConfiguration.userName());
    }

    @Test
    public void testEmail() {
        assert user.email().equals(TestUserConfiguration.userEmail());
    }

    @Test
    public void testPhone() {
        assert user.phone().equals(TestUserConfiguration.userPhone());
    }
}
