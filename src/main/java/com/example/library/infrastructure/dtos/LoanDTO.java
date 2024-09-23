package com.example.library.infrastructure.dtos;

import com.example.library.core.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
        private LoanStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate loanDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate returnDate;
        private Long userId;
        private Long bookId;

        public String publishDateFormatted() {
                return loanDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
}
