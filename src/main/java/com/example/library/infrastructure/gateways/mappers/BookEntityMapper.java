package com.example.library.infrastructure.gateways.mappers;

import com.example.library.core.entities.Book;
import com.example.library.infrastructure.persistence.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMapper {

    public BookEntity toEntity(Book book) {
        return new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getPublishDate()
        );
    }

    public Book toBook(BookEntity entity) {
        return new Book(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getIsbn(),
            entity.getCategory(),
            entity.getPublishDate()
        );
    }
}
