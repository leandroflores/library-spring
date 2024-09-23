package com.example.library.core.use_cases.loans.implementations;

import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.exceptions.BusinessException;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.GetLoansByUserUseCase;

import java.util.List;

public class GetLoansByUserUseCaseImplementation implements GetLoansByUserUseCase {
    private final LoanGateway gateway;

    public GetLoansByUserUseCaseImplementation(LoanGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Loan> execute(User user) {
        if (user == null) {
            throw new BusinessException("User not found");
        }
        return gateway.getLoansByUser(user);
    }
}
