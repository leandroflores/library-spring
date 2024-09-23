package com.example.library.infrastructure.persistence;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

public class BookEntityTest {

    @Mock
    private BookEntity book;

    @BeforeEach
    public void setUp() {
        book = TestBookConfiguration.bookEntity();
    }

    @Test
    public void testGetId() throws Exception {
        Long id = TestBookConfiguration.bookId();
        assert book.getId().equals(id);
    }

    @Test
    public void testGetTitle() throws Exception {
        String title = TestBookConfiguration.bookTitle();
        assert book.getTitle().equals(title);
    }

    @Test
    public void testGetAuthor() throws Exception {
        String author = TestBookConfiguration.bookAuthor();
        assert book.getAuthor().equals(author);
    }

    @Test
    public void testGetISBN() throws Exception {
        String ISBN = TestBookConfiguration.bookISBN();
        assert book.getIsbn().equals(ISBN);
    }

    @Test
    public void testGetCategory() throws Exception {
        String category = TestBookConfiguration.bookCategory();
        assert book.getCategory().equals(category);
    }

    @Test
    public void testGetPublishedDate() throws Exception {
        LocalDate publishedDate = TestBookConfiguration.bookPublishDate();
        assert book.getPublishDate().equals(publishedDate);
    }
}
