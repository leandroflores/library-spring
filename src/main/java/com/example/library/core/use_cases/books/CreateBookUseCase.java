package com.example.library.core.use_cases.books;

import com.example.library.core.entities.Book;

public interface CreateBookUseCase {

    public Book execute(Book book);
}
