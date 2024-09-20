package com.example.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table (name = "books")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title can not be empty")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author can not be empty")
    @Column(nullable = false)
    private String author;

    @NotNull(message = "ISBN is required")
    @NotEmpty(message = "ISBN can not be empty")
    @Column(nullable = false)
    private String isbn;

    @NotNull(message = "Category is required")
    @NotEmpty(message = "Category can not be empty")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "Publish date  is required")
    @PastOrPresent(message = "Publish date can not be a future date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    public String publishDateFormatted() {
        return this.publishDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError();
        }
    }
}
