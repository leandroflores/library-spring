package com.example.library.infrastructure.controllers;

import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.use_cases.loans.*;
import com.example.library.infrastructure.controllers.mappers.LoanMapper;
import com.example.library.infrastructure.dtos.LoanCreateDTO;
import com.example.library.infrastructure.dtos.LoanDTO;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("loans/")
@AllArgsConstructor
public class LoanController {
    private final CancelLoanUseCase cancelLoanUseCase;
    private final CreateLoanUseCase createLoanUseCase;
    private final FinalizeLoanUseCase finalizeLoanUseCase;
    private final GetLoanUseCase getLoanUseCase;
    private final GetLoansUseCase getLoansUseCase;
    private final GetLoansByUserUseCase getLoansByUserUseCase;
    private final UserRepository userRepository;
    private final LoanMapper mapper;
    private final UserEntityMapper userMapper;

    @GetMapping
    public List<LoanDTO> getAllLoans() {
        return getLoansUseCase
                .execute()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{loanId}")
    public LoanDTO getLoanById(@PathVariable Long loanId) {
        Loan loan = getLoanUseCase.execute(loanId);
        return mapper.toDTO(loan);
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanCreateDTO loan) {
        Loan entity = mapper.toNewLoan(loan);
        Loan newLoan = createLoanUseCase.execute(entity);
        LoanDTO newLoanDTO = mapper.toDTO(newLoan);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLoanDTO);
    }

    @PatchMapping("/{loanId}")
    public ResponseEntity<?> finalizeLoan(@PathVariable Long loanId) {
        Loan loan = getLoanUseCase.execute(loanId);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }
        finalizeLoanUseCase.execute(loan);
        return ResponseEntity.status(HttpStatus.OK).body("Loan has been finalized");
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<?> cancelLoan(@PathVariable Long loanId) {
        Loan loan = getLoanUseCase.execute(loanId);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }
        cancelLoanUseCase.execute(loan);
        return ResponseEntity.status(HttpStatus.OK).body("Loan has been cancelled");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLoansByUser(@PathVariable Long userId) {
        UserEntity entity = userRepository.findUserById(userId).orElse(null);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = userMapper.toUser(entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getLoansByUserUseCase
                        .execute(user)
                        .stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList())
                );
    }

}
