package com.example.library.infrastructure.persistence;

import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.entities.User;
import com.example.library.core.enums.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

public class LoanEntityTest {

    @Mock
    private LoanEntity loan;

    @BeforeEach
    public void setUp() {
        loan = TestLoanConfiguration.loanEntity();
    }

    @Test
    public void testGetId() throws Exception {
        Long id = TestLoanConfiguration.loanId();
        assert loan.getId().equals(id);
    }

    @Test
    public void testGetStatus() throws Exception {
        LoanStatus status = TestLoanConfiguration.loanStatus();
        assert loan.getStatus().equals(status);
    }

    @Test
    public void testGetLoanDate() throws Exception {
        LocalDate loanDate = TestLoanConfiguration.loanDate();
        assert loan.getLoanDate().equals(loanDate);
    }

    @Test
    public void testGetReturnDate() throws Exception {
        LocalDate returnDate = TestLoanConfiguration.loanReturnDate();
        assert loan.getReturnDate().equals(returnDate);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = TestLoanConfiguration.loanUser();
        assert loan.getUser().getId().equals(user.getId());
    }

    @Test
    public void testGetBook() throws Exception {
        Book book = TestLoanConfiguration.loanBook();
        assert loan.getBook().getId().equals(book.getId());
    }
}