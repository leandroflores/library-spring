package com.example.library.core.use_cases.books;

import com.example.library.core.entities.Book;

import java.util.List;

public interface GetAllBooksUseCase {

    public List<Book> execute();
}
