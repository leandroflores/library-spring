package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.GetLoansUseCase;

import java.util.List;

public class GetLoansUseCaseImplementation implements GetLoansUseCase {
    private final LoanGateway gateway;

    public GetLoansUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Loan> execute() {
        return gateway.getAllLoans();
    }
}
