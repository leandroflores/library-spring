package com.example.library.infrastructure.dtos;

import com.example.library.configuration.TestLoanConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class LoanDTOTest {

    @Mock
    private LoanDTO loan;

    @BeforeEach
    public void setUp() {
        loan = TestLoanConfiguration.loanDTO();
    }

    @Test
    public void testGetStatus() {
        assert loan.getStatus().equals(TestLoanConfiguration.loanStatus());
    }

    @Test
    public void testGetLoanDate() {
        assert loan.getLoanDate().equals(TestLoanConfiguration.loanDate());
    }

    @Test
    public void testGetReturnDate() {
        assert loan.getReturnDate().equals(TestLoanConfiguration.loanReturnDate());
    }

    @Test
    public void testGetUserId() {
        assert loan.getUserId().equals(TestLoanConfiguration.loanUser().getId());
    }

    @Test
    public void testGetBookId() {
        assert loan.getBookId().equals(TestLoanConfiguration.loanBook().getId());
    }
}
