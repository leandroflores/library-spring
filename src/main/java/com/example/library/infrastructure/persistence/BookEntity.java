package com.example.library.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Author can not be empty")
    @Column(nullable = false)
    private String author;

    @NotEmpty(message = "ISBN can not be empty")
    @Column(nullable = false)
    private String isbn;

    @NotEmpty(message = "Category can not be empty")
    @Column(nullable = false)
    private String category;

    @PastOrPresent(message = "Publish Date can not be a future date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
}
