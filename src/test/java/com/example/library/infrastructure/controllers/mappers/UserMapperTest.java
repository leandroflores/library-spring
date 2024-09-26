package com.example.library.infrastructure.controllers.mappers;

import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.User;
import com.example.library.infrastructure.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserMapperTest {

    @Mock
    private UserMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.mapper();
    }

    @Test
    public void testToUser() {
        UserDTO userDTO = TestUserConfiguration.userDTO();

        User user = mapper.toUser(userDTO);

        assert user.getName().equals(TestUserConfiguration.userName());
        assert user.getEmail().equals(TestUserConfiguration.userEmail());
        assert user.getPhone().equals(TestUserConfiguration.userPhone());    }

    @Test
    public void testToDTO() {
        User user = TestUserConfiguration.user();

        UserDTO userDTO = mapper.toDTO(user);

        assert userDTO.name().equals(TestUserConfiguration.userName());
        assert userDTO.email().equals(TestUserConfiguration.userEmail());
        assert userDTO.phone().equals(TestUserConfiguration.userPhone());
    }
}
