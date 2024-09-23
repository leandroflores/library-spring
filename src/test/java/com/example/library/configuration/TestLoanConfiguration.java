package com.example.library.configuration;

import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.enums.LoanStatus;
import com.example.library.infrastructure.gateways.mappers.LoanEntityMapper;
import com.example.library.infrastructure.persistence.LoanEntity;

import java.time.LocalDate;

public class TestLoanConfiguration {
    private static final LoanEntityMapper LOAN_ENTITY_MAPPER = new LoanEntityMapper(
            TestBookConfiguration.entityMapper(),
            TestUserConfiguration.entityMapper()
    );

    public static LoanEntityMapper entityMapper() {
        return LOAN_ENTITY_MAPPER;
    }

    public static Long loanId() {
        return 23L;
    }

    public static LoanStatus loanStatus() {
        return LoanStatus.ACTIVE;
    }

    public static LocalDate loanDate() {
        return LocalDate.now().minusDays(3);
    }

    public static LocalDate loanReturnDate() {
        return LocalDate.now();
    }

    public static User loanUser() {
        return TestUserConfiguration.user();
    }

    public static Book loanBook() {
        return TestBookConfiguration.book();
    }

    public static Loan loan() {
        return new Loan(
                loanId(),
                loanStatus(),
                loanDate(),
                loanReturnDate(),
                loanUser(),
                loanBook()
        );
    }

    public static LoanEntity loanEntity() {
        return new LoanEntity(
                loanId(),
                loanStatus(),
                loanDate(),
                loanReturnDate(),
                TestUserConfiguration.userEntity(),
                TestBookConfiguration.bookEntity()
        );
    }
}