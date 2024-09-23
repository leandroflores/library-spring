package com.example.library.infrastructure.gateways.mappers;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.infrastructure.persistence.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BookEntityMapperTest {

    @Mock
    private BookEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
    }

    @Test
    public void testToEntity() {
        Book book = TestBookConfiguration.book();
        BookEntity entity = TestBookConfiguration.bookEntity();

        assert mapper.toEntity(book).equals(entity);
    }

    @Test
    public void testToBook() {
        BookEntity entity = TestBookConfiguration.bookEntity();
        Book book = TestBookConfiguration.book();

        assert mapper.toBook(entity).equals(book);
    }
}
