package com.example.library.core.use_cases.books.implementations;

import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.DeleteBookUseCase;

public class DeleteBookUseCaseImplementation implements DeleteBookUseCase {
    private final BookGateway gateway;

    public DeleteBookUseCaseImplementation(BookGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean execute(Long id) {
        boolean deleted = gateway.deleteBook(id);
        if (!deleted) {
            throw new EntityNotFoundException("Book", "Id", id);
        }
        return true;
    }
}
