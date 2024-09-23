package com.example.library.core.use_cases.books.implementations;

import com.example.library.core.entities.Book;
import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.GetAllBooksUseCase;

import java.util.List;

public class GetAllBooksUseCaseImplementation implements GetAllBooksUseCase {
    private final BookGateway gateway;

    public GetAllBooksUseCaseImplementation(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Book> execute() {
        return gateway.getAllBooks();
    }
}
