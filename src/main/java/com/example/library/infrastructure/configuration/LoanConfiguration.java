package com.example.library.infrastructure.configuration;

import com.example.library.core.gateways.LoanGateway;
import com.example.library.core.use_cases.loans.*;
import com.example.library.core.use_cases.loans.implementations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanConfiguration {

    @Bean
    public GetLoanUseCase getLoanUseCase(LoanGateway gateway) {
        return new GetLoanUseCaseImplementation(gateway);
    }

    @Bean
    public CancelLoanUseCase getCancelLoanUseCase(LoanGateway gateway) {
        return new CancelLoanUseCaseImplementation(gateway);
    }

    @Bean
    public CreateLoanUseCase createLoanUseCase(LoanGateway gateway) {
        return new CreateLoanUseCaseImplementation(gateway);
    }

    @Bean
    public FinalizeLoanUseCase finalizeLoanUseCase(LoanGateway gateway) {
        return new FinalizeLoanUseCaseImplementation(gateway);
    }

    @Bean
    public GetLoansUseCase getLoansUseCase(LoanGateway gateway) {
        return new GetLoansUseCaseImplementation(gateway);
    }

    @Bean
    public GetLoansByUserUseCase getLoansByUserUseCase(LoanGateway gateway) {
        return new GetLoansByUserUseCaseImplementation(gateway);
    }
}
