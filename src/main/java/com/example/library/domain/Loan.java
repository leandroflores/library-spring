package com.example.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Loan implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public String loanDateFormatted() {
        return this.loanDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String returnDateFormatted() {
        return this.returnDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public Loan clone() {
        try {
            return (Loan) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
