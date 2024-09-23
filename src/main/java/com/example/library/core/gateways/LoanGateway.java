package com.example.library.core.gateways;

import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;

import java.util.List;
import java.util.Optional;

public interface LoanGateway {

    Loan createLoan(Loan loan);

    Optional<Loan> getLoan(Long id);

    Optional<Loan> getActiveLoan(Book book);

    Loan saveLoan(Loan loan);

    List<Loan> getLoansByUser(User user);

    List<Loan> getAllLoans();
}
