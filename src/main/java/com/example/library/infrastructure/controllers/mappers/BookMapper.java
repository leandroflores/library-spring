package com.example.library.infrastructure.controllers.mappers;

import com.example.library.core.entities.Book;
import com.example.library.infrastructure.dtos.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toBook(BookDTO book) {
        return new Book(
                null,
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getPublishDate()
        );
    }

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getPublishDate()
        );
    }
}
