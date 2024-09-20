package com.example.library.domain.dto;

import com.example.library.domain.Loan;
import com.example.library.domain.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private Long userId;
    private Long bookId;
    private String status;

    @NotNull(message = "Loan date  is required")
    @NotEmpty(message = "Loan date  can not be empty")
    @PastOrPresent(message = "Loan date can not be a future date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @PastOrPresent(message = "Return date can not be a future date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "return_date")
    private LocalDate returnDate;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.bookId = loan.getBook().getId();
        this.userId = loan.getUser().getId();
        this.status = loan.getStatus().toString();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }

    public String loanDateFormatted() {
        return this.loanDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String returnDateFormatted() {
        return this.returnDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
