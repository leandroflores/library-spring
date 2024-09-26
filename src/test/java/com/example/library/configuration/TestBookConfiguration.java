package com.example.library.configuration;

import com.example.library.core.entities.Book;
import com.example.library.infrastructure.controllers.mappers.BookMapper;
import com.example.library.infrastructure.dtos.BookDTO;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;

import java.time.LocalDate;
import java.util.List;

public class TestBookConfiguration {
    private static final BookMapper MAPPER = new BookMapper();
    private static final BookEntityMapper ENTITY_MAPPER = new BookEntityMapper();

    public static BookMapper mapper() {
        return MAPPER;
    }

    public static BookEntityMapper entityMapper() {
        return ENTITY_MAPPER;
    }

    public static Long bookId() {
        return 1L;
    }

    public static String bookTitle() {
        return "Dom Casmurro";
    }

    public static String bookAuthor() {
        return "Machado de Assis";
    }

    public static String bookISBN() {
        return "9788542221091";
    }

    public static String bookCategory() {
        return "Romance";
    }

    public static LocalDate bookPublishDate() {
        return LocalDate.of(1899, 12, 5);
    }

    public static Book book() {
        return new Book(
                bookId(),
                bookTitle(),
                bookAuthor(),
                bookISBN(),
                bookCategory(),
                bookPublishDate()
        );
    }

    public static BookEntity bookEntity() {
        return new BookEntity(
                bookId(),
                bookTitle(),
                bookAuthor(),
                bookISBN(),
                bookCategory(),
                bookPublishDate()
        );
    }

    public static List<Book> books() {
        return List.of(book());
    }

    public static BookDTO bookDTO() {
        return new BookMapper().toDTO(book());
    }
}
