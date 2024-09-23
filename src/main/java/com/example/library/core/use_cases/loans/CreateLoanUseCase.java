package com.example.library.core.use_cases.loans;

import com.example.library.core.entities.Loan;

public interface CreateLoanUseCase {

    public Loan execute(Loan loan);
}
