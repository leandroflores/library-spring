package com.example.library.repository;

import com.example.library.domain.Loan;
import com.example.library.domain.LoanStatus;
import com.example.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT loan FROM Loan loan WHERE loan.status = 'ACTIVE' AND loan.book.id = :bookId")
    Optional<Loan> findActiveLoanByBook(@Param("bookId") Long bookId);

    List<Loan> findByUser(User user);

}
