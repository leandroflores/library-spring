package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.enums.LoanStatus;
import com.example.library.core.exceptions.BusinessException;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.CancelLoanUseCase;

import java.time.LocalDate;

public class CancelLoanUseCaseImplementation implements CancelLoanUseCase {
    private final LoanGateway gateway;

    public CancelLoanUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Loan execute(Loan loan) {
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new BusinessException("Status loan is not active");
        }
        loan.setStatus(LoanStatus.CANCELLED);
        loan.setReturnDate(LocalDate.now());
        return gateway.saveLoan(loan);
    }
}
