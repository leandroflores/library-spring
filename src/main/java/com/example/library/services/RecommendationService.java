package com.example.library.services;

import com.example.library.domain.Book;
import com.example.library.domain.Loan;
import com.example.library.domain.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Book> recommendBooks(User user) {
        List<Loan> loans = loanRepository.findByUser(user);
        List<String> categories = getCategories(loans);
        return bookRepository
                .findByCategoryIn(categories)
                .stream()
                .filter(book -> loans.stream()
                    .map(Loan::getBook)
                    .noneMatch(book::equals))
                .toList();
    }

    private List<String> getCategories(List<Loan> loans) {
        return loans
                .stream()
                .map(loan -> loan.getBook().getCategory())
                .distinct()
                .toList();
    }
}
