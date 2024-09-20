package com.example.library.services;

import com.example.library.domain.Loan;
import com.example.library.domain.LoanStatus;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean checkUser(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    public boolean checkBook(Long bookId) {
        return bookRepository.findById(bookId).isPresent();
    }

    @Transactional
    public Optional<Loan> createLoan(Loan loan) {
        Optional<Loan> loanExists = repository.findActiveLoanByBook(loan.getBook().getId());
        if (loanExists.isEmpty()) {
            return Optional.of(repository.save(loan));
        }
        return Optional.empty();
    }

    public Optional<Loan> getLoanById(Long loanId) {
        return repository.findById(loanId);
    }

    @Transactional
    public Optional<Loan> finishLoan(Long id) {
        Optional<Loan> loanById = repository.findById(id);
        if (loanById.isPresent()) {
            Loan loanFound = loanById.get();
            loanFound.setStatus(LoanStatus.COMPLETED);
            loanFound.setLoanDate(LocalDate.now());
            return Optional.of(repository.save(loanFound));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteLoan(Long id) {
        Optional<Loan> loanById = repository.findById(id);
        if (loanById.isPresent()) {
            Loan loanFound = loanById.get();
            loanFound.setStatus(LoanStatus.CANCELLED);
            repository.save(loanFound);
            return true;
        }
        return false;
    }

    public List<Loan> getAllLoans() {
        return repository.findAll();
    }
}
