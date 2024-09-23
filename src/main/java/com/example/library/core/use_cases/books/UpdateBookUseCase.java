package com.example.library.core.use_cases.books;

import com.example.library.core.entities.Book;

public interface UpdateBookUseCase {

    public Book execute(Long id, Book client);
}
