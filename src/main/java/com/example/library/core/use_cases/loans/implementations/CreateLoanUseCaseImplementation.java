package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.enums.LoanStatus;
import com.example.library.core.exceptions.BusinessException;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.CreateLoanUseCase;

public class CreateLoanUseCaseImplementation implements CreateLoanUseCase {
    private final LoanGateway gateway;

    public CreateLoanUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Loan execute(Loan loan) {
        if (loan.getUser() == null) {
            throw new BusinessException("User not found");
        }
        if (loan.getBook() == null) {
            throw new BusinessException("Book not found");
        }
        if (gateway.getActiveLoan(loan.getBook()).isPresent()) {
            throw new BusinessException("Book already is borrowed");
        }
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setReturnDate(null);
        return gateway.createLoan(loan);
    }
}
