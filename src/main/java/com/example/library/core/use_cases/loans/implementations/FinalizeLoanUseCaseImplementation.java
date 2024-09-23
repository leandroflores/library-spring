package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.enums.LoanStatus;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.FinalizeLoanUseCase;

import java.time.LocalDate;

public class FinalizeLoanUseCaseImplementation implements FinalizeLoanUseCase {
    private final LoanGateway gateway;

    public FinalizeLoanUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Loan execute(Loan loan) {
        loan.setStatus(LoanStatus.COMPLETED);
        loan.setReturnDate(LocalDate.now());
        return gateway.saveLoan(loan);
    }
}
