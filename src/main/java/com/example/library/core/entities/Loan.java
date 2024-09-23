package com.example.library.core.entities;


import com.example.library.core.enums.LoanStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Loan {
    private Long id;
    private LoanStatus status;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private User user;
    private Book book;
}
