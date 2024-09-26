package com.example.library.infrastructure.dtos;

import com.example.library.configuration.TestLoanConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class LoanCreateDTOTest {

    @Mock
    private LoanCreateDTO loan;

    @BeforeEach
    public void setUp() {
        loan = TestLoanConfiguration.loanCreateDTO();
    }

    @Test
    public void testUserId() {
        assert loan.getUserId().equals(TestLoanConfiguration.loanUser().getId());
    }

    @Test
    public void testBookId() {
        assert loan.getBookId().equals(TestLoanConfiguration.loanBook().getId());
    }
}
