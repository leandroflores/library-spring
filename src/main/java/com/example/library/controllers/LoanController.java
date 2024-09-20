package com.example.library.controllers;

import com.example.library.domain.Loan;
import com.example.library.domain.LoanStatus;
import com.example.library.domain.dto.LoanDTO;
import com.example.library.services.BookService;
import com.example.library.services.LoanService;
import com.example.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(
                service.getAllLoans().stream()
                        .map(LoanDTO::new)
                        .collect(Collectors.toList()
                )
        );
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<?> getLoan(@PathVariable Long loanId) {
        try {
            Optional<Loan> loan = service.getLoanById(loanId);
            if (loan.isPresent()) {
                return ResponseEntity.ok(new LoanDTO(loan.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createLoan(@RequestBody LoanDTO loanDTO) {
        try {
            if (!service.checkUser(loanDTO.getUserId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (!service.checkBook(loanDTO.getBookId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
            Loan loan = new Loan();
                 loan.setStatus(LoanStatus.ACTIVE);
                 loan.setLoanDate(loanDTO.getLoanDate());
                 loan.setReturnDate(null);
                 loan.setUser(userService.getUserById(loanDTO.getUserId()).orElse(null));
                 loan.setBook(bookService.getBookById(loanDTO.getBookId()).orElse(null));

            Optional<Loan> result = service.createLoan(loan);
            System.out.println(result);
            if (result.isPresent()) {
                LoanDTO newLoan = new LoanDTO(result.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book is not available");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @PatchMapping("/{loan_id}")
    public ResponseEntity<Object> finishLoan(@PathVariable Long loan_id) {
        try {
            Optional<Loan> loan = service.finishLoan(loan_id);
            if (loan.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
            }
            return ResponseEntity.ok("Loan finished successfully");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{loan_id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long loan_id) {
        try {
            boolean deletedLoan = service.deleteLoan(loan_id);
            if (!deletedLoan) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
            }
            return ResponseEntity.ok("Loan cancelled successfully");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
}
