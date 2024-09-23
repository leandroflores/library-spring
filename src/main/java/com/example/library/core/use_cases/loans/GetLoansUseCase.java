package com.example.library.core.use_cases.loans;

import com.example.library.core.entities.Loan;

import java.util.List;

public interface GetLoansUseCase {

    public List<Loan> execute();
}
