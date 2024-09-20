package com.example.library.controllers;

import com.example.library.TestConfig;
import com.example.library.domain.Loan;
import com.example.library.domain.LoanStatus;
import com.example.library.domain.dto.LoanDTO;
import com.example.library.services.BookService;
import com.example.library.services.LoanService;
import com.example.library.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class LoanControllerTest {

    private MockMvc mvc;

    @Mock
    private LoanService service;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetLoans() throws Exception {
        List<Loan> loans = TestConfig.loans();
        LoanDTO loan = new LoanDTO(loans.get(0));

        when(service.getAllLoans()).thenReturn(loans);

        mvc.perform(MockMvcRequestBuilders.get("/loans/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(loan.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].book_id").value(loan.getBookId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user_id").value(loan.getBookId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(loan.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].loanDate").value(loan.loanDateFormatted()));
    }

    @Test
    public void testGetLoan() throws Exception {
        LoanDTO loan = new LoanDTO(TestConfig.loan());

        when(service.getLoanById(loan.getId())).thenReturn(Optional.of(TestConfig.loan()));

        mvc.perform(MockMvcRequestBuilders.get("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(loan.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("book_id").value(loan.getBookId()))
                .andExpect(MockMvcResultMatchers.jsonPath("user_id").value(loan.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(loan.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("loanDate").value(loan.loanDateFormatted()));
    }

    @Test
    public void testGetNotFoundLoan() throws Exception {
        Loan loan = TestConfig.loan();

        when(service.getLoanById(loan.getId())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Loan not found"));
    }

    @Test
    public void testCreateLoan() throws Exception {
        Loan loan  = TestConfig.loan().clone();
             loan.setStatus(LoanStatus.PENDING);
        LoanDTO loan_ = new LoanDTO(loan);
        String json = TestConfig.createMapper().writeValueAsString(loan_);

        when(service.checkUser(loan_.getUserId())).thenReturn(true);
        when(service.checkBook(loan_.getBookId())).thenReturn(true);
        when(bookService.getBookById(loan_.getBookId())).thenReturn(Optional.of(TestConfig.book()));
        when(userService.getUserById(loan_.getUserId())).thenReturn(Optional.of(TestConfig.user()));
        when(service.createLoan(loan)).thenReturn(Optional.of(loan));

        mvc.perform(MockMvcRequestBuilders.post("/loans/")
           .contentType(MediaType.APPLICATION_JSON)
           .content(json))
           .andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    public void testCreateLoanWithUserNotFound() throws Exception {
        Loan loan  = TestConfig.loan().clone();
        LoanDTO loan_ = new LoanDTO(loan);
        String json = TestConfig.createMapper().writeValueAsString(loan_);

        when(service.checkUser(loan_.getUserId())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.post("/loans/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    public void testCreateLoanWithBookNotFound() throws Exception {
        Loan loan  = TestConfig.loan().clone();
        LoanDTO loan_ = new LoanDTO(loan);
        String json = TestConfig.createMapper().writeValueAsString(loan_);

        when(service.checkUser(loan_.getUserId())).thenReturn(true);
        when(service.checkBook(loan_.getBookId())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.post("/loans/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book not found"));
    }

    @Test
    public void testCreateLoanWithBookNotAvailable() throws Exception {
        Loan loan  = TestConfig.loan().clone();
        LoanDTO loan_ = new LoanDTO(loan);
        String json = TestConfig.createMapper().writeValueAsString(loan_);

        when(service.checkUser(loan_.getUserId())).thenReturn(true);
        when(service.checkBook(loan_.getBookId())).thenReturn(true);
        when(service.createLoan(loan)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post("/loans/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Book is not available"));
    }

    @Test
    public void testFinishLoan() throws Exception {
        Loan loan  = TestConfig.loan().clone();

        when(service.finishLoan(loan.getId())).thenReturn(Optional.of(loan));

        mvc.perform(MockMvcRequestBuilders.patch("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Loan finished successfully"));
    }

    @Test
    public void testFinishLoanNotFound() throws Exception {
        Loan loan = TestConfig.loan();

        when(service.finishLoan(loan.getId())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.patch("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Loan not found"));
    }

    @Test
    public void testDeleteLoan() throws Exception {
        Loan loan = TestConfig.loan();

        when(service.deleteLoan(loan.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Loan cancelled successfully"));
    }

    @Test
    public void testDeleteLoanNotFound() throws Exception {
        Loan loan = TestConfig.loan();

        when(service.deleteLoan(loan.getId())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/loans/" + loan.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Loan not found"));
    }
}
