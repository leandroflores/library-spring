package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.exceptions.EntityNotFoundException;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.GetLoanUseCase;

import java.util.Optional;

public class GetLoanUseCaseImplementation implements GetLoanUseCase {
    private final LoanGateway gateway;

    public GetLoanUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Loan execute(Long id) {
        Optional<Loan> loan = gateway.getLoan(id);
        if (loan.isEmpty()) {
            throw new EntityNotFoundException("Loan", "Id", id);
        }
        return loan.get();
    }
}
