package com.example.library.core.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private LocalDate publishDate;
}
