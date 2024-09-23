package com.example.library.core.use_cases.books;

import com.example.library.core.entities.Book;

public interface GetBookUseCase {

    public Book execute(Long id);
}
