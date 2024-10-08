package com.example.library.infrastructure.persistence;

import com.example.library.configuration.TestBookConfiguration;
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
    public void testGetId() {
        Long id = TestBookConfiguration.bookId();
        assert book.getId().equals(id);
    }

    @Test
    public void testGetTitle() {
        String title = TestBookConfiguration.bookTitle();
        assert book.getTitle().equals(title);
    }

    @Test
    public void testGetAuthor() {
        String author = TestBookConfiguration.bookAuthor();
        assert book.getAuthor().equals(author);
    }

    @Test
    public void testGetISBN() {
        String ISBN = TestBookConfiguration.bookISBN();
        assert book.getIsbn().equals(ISBN);
    }

    @Test
    public void testGetCategory() {
        String category = TestBookConfiguration.bookCategory();
        assert book.getCategory().equals(category);
    }

    @Test
    public void testGetPublishedDate() {
        LocalDate publishedDate = TestBookConfiguration.bookPublishDate();
        assert book.getPublishDate().equals(publishedDate);
    }
}
