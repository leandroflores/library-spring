package com.example.library.core.use_cases.books.implementations;

import com.example.library.core.entities.Book;
import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.GetBookUseCase;

import java.util.Optional;

public class GetBookUseCaseImplementation implements GetBookUseCase {
    private final BookGateway gateway;

    public GetBookUseCaseImplementation(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Book execute(Long id) {
        Optional<Book> book = gateway.getBook(id);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("Book", "Id", id);
        }
        return book.get();
    }
}
