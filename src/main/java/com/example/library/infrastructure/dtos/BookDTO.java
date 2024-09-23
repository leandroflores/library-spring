package com.example.library.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
        @NotEmpty(message = "Title can not be empty")
        private String title;
        private String author;
        private String isbn;
        private String category;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate publishDate;

        public String publishDateFormatted() {
                return publishDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
}
