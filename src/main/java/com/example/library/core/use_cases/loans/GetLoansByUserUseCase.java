package com.example.library.core.use_cases.loans;

import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;

import java.util.List;

public interface GetLoansByUserUseCase {

    public List<Loan> execute(User user);
}
