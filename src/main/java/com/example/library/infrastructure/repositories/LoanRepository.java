package com.example.library.infrastructure.repositories;

import com.example.library.core.enums.LoanStatus;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.LoanEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Optional<LoanEntity> findLoanById(Long id);

    Optional<LoanEntity> findByStatusAndBook(LoanStatus status, BookEntity book);

    List<LoanEntity> findLoansByUser(UserEntity user);
}
