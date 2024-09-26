package com.example.library.infrastructure.controllers.mappers;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.infrastructure.dtos.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BookMapperTest {

    @Mock
    private BookMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.mapper();
    }

    @Test
    public void testToBook() {
        BookDTO bookDTO = TestBookConfiguration.bookDTO();

        Book book = mapper.toBook(bookDTO);

        assert book.getTitle().equals(TestBookConfiguration.bookTitle());
        assert book.getAuthor().equals(TestBookConfiguration.bookAuthor());
        assert book.getIsbn().equals(TestBookConfiguration.bookISBN());
        assert book.getCategory().equals(TestBookConfiguration.bookCategory());
        assert book.getPublishDate().equals(TestBookConfiguration.bookPublishDate());
    }

    @Test
    public void testToDTO() {
        Book book = TestBookConfiguration.book();

        BookDTO bookDTO = mapper.toDTO(book);

        assert bookDTO.getTitle().equals(TestBookConfiguration.bookTitle());
        assert bookDTO.getAuthor().equals(TestBookConfiguration.bookAuthor());
        assert bookDTO.getIsbn().equals(TestBookConfiguration.bookISBN());
        assert bookDTO.getCategory().equals(TestBookConfiguration.bookCategory());
        assert bookDTO.getPublishDate().equals(TestBookConfiguration.bookPublishDate());
    }
}
