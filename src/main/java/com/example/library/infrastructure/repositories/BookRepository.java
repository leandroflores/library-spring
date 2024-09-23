package com.example.library.infrastructure.repositories;

import com.example.library.infrastructure.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findBookById(Long id);
}
