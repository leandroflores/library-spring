package com.example.library.core.use_cases.books.implementations;

import com.example.library.core.entities.Book;
import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.UpdateBookUseCase;

import java.util.Optional;

public class UpdateBookUseCaseImplementation implements UpdateBookUseCase {
    private final BookGateway gateway;

    public UpdateBookUseCaseImplementation(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Book execute(Long id, Book book) {
        Optional<Book> bookFound = gateway.getBook(id);
        if (bookFound.isEmpty()) {
            throw new EntityNotFoundException("Book", "Id", id);
        }
        bookFound.get().setTitle(book.getTitle());
        bookFound.get().setAuthor(book.getAuthor());
        bookFound.get().setIsbn(book.getIsbn());
        bookFound.get().setCategory(book.getCategory());
        bookFound.get().setPublishDate(book.getPublishDate());
        return gateway.saveBook(bookFound.get());
    }
}
