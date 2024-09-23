package com.example.library.infrastructure.controllers;

import com.example.library.core.entities.Book;
import com.example.library.core.use_cases.books.*;
import com.example.library.infrastructure.controllers.mappers.BookMapper;
import com.example.library.infrastructure.dtos.BookDTO;
import com.example.library.infrastructure.dtos.ResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books/")
@AllArgsConstructor
public class BookController {
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final GetBookUseCase getBookUseCase;
    private final CreateBookUseCase createBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final BookMapper mapper;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return getAllBooksUseCase
                .execute()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable Long bookId) {
        Book book = getBookUseCase.execute(bookId);
        return mapper.toDTO(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book) {
        Book entity = mapper.toBook(book);
        Book newBook = createBookUseCase.execute(entity);
        BookDTO newBookDTO = mapper.toDTO(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBookDTO);
    }

    @DeleteMapping("/{bookId}")
    public ResponseDTO deleteBook(@PathVariable Long bookId) {
        boolean deleted = deleteBookUseCase.execute(bookId);
        if (deleted) {
            return new ResponseDTO("Book deleted successfully");
        }
        return new ResponseDTO("Error deleting Book");
    }

    @PutMapping("/{bookId}")
    public BookDTO updateBook(@PathVariable Long bookId, @Valid @RequestBody BookDTO book) {
        Book entity = mapper.toBook(book);
        Book updatedBook = updateBookUseCase.execute(bookId, entity);
        return mapper.toDTO(updatedBook);
    }
}
