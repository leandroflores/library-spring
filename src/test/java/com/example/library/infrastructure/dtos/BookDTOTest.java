package com.example.library.infrastructure.dtos;

import com.example.library.configuration.TestBookConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.format.DateTimeFormatter;

public class BookDTOTest {

    @Mock
    private BookDTO book;

    @BeforeEach
    public void setUp() {
        book = TestBookConfiguration.bookDTO();
    }

    @Test
    public void testGetTitle() {
        assert book.getTitle().equals(TestBookConfiguration.bookTitle());
    }

    @Test
    public void testGetAuthor() {
        assert book.getAuthor().equals(TestBookConfiguration.bookAuthor());
    }

    @Test
    public void testGetISBN() {
        assert book.getIsbn().equals(TestBookConfiguration.bookISBN());
    }

    @Test
    public void testGetCategory() {
        assert book.getCategory().equals(TestBookConfiguration.bookCategory());
    }

    @Test
    public void testGetPublishDate() {
        assert book.getPublishDate().equals(TestBookConfiguration.bookPublishDate());
    }

    @Test
    public void testPublishDateFormatted() {
        assert book.publishDateFormatted().equals(
                TestBookConfiguration.bookPublishDate().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                )
        );
    }
}
