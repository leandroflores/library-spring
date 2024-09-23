package com.example.library.core.gateways;

import com.example.library.core.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookGateway {

    Book createBook(Book book);

    Optional<Book> getBook(Long id);

    Book saveBook(Book book);

    boolean deleteBook(Long id);

    List<Book> getAllBooks();
}
