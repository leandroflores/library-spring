package com.example.library.infrastructure.gateways.mappers;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.entities.User;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserEntityMapperTest {

    @Mock
    private UserEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TestUserConfiguration.entityMapper();
    }

    @Test
    public void testToEntity() {
        User user = TestUserConfiguration.user();
        UserEntity entity = TestUserConfiguration.userEntity();

        assert mapper.toEntity(user).equals(entity);
    }

    @Test
    public void testToUser() {
        UserEntity entity = TestUserConfiguration.userEntity();
        User user = TestUserConfiguration.user();

        assert mapper.toUser(entity).equals(user);
    }
}
