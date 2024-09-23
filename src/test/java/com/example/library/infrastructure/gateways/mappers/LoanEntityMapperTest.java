package com.example.library.infrastructure.gateways.mappers;

import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.core.entities.Loan;
import com.example.library.infrastructure.persistence.LoanEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class LoanEntityMapperTest {

    @Mock
    private LoanEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TestLoanConfiguration.entityMapper();
    }

    @Test
    public void testToEntity() {
        Loan loan = TestLoanConfiguration.loan();
        LoanEntity entity = TestLoanConfiguration.loanEntity();

        assert mapper.toEntity(loan).equals(entity);
    }

    @Test
    public void testToLoan() {
        LoanEntity entity = TestLoanConfiguration.loanEntity();
        Loan loan = TestLoanConfiguration.loan();

        assert mapper.toLoan(entity).equals(loan);
    }
}
