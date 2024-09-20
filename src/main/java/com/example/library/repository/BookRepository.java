package com.example.library.repository;


import com.example.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategoryIn(List<String> categories);
}
