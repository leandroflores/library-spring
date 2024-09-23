package com.example.library.core.use_cases.books.implementations;

import com.example.library.core.entities.Book;
import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.CreateBookUseCase;

public class CreateBookUseCaseImplementation implements CreateBookUseCase {
    private final BookGateway gateway;

    public CreateBookUseCaseImplementation(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Book execute(Book book) {
        return gateway.createBook(book);
    }
}
