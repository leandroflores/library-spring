package com.example.library.controllers;

import com.example.library.domain.Book;
import com.example.library.services.BookService;
import com.example.library.services.google.BooksAPIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(
                service.getAllBooks()
        );
    }

    @GetMapping("/api/")
    public Mono<ResponseEntity<String>> getBooksFromAPI(@RequestParam String title) {
        return BooksAPIService.getBooks(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBook(@PathVariable Long bookId) {
        try {
            Optional<Book> book = service.getBookById(bookId);
            if (book.isPresent()) {
                return ResponseEntity.ok(book.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createBook(@Valid @RequestBody Book book) {
        try {
            Book newBook = service.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        try {
            boolean deleteBook = service.deleteBook(bookId);
            if (!deleteBook) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
            return ResponseEntity.ok("Book deleted successfully");
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateUser(@PathVariable Long bookId, @Valid @RequestBody Book book) {
        try {
            Optional<Book> updatedBook = service.updateBook(bookId, book);
            if (updatedBook.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
            return ResponseEntity.ok(updatedBook);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Objects.requireNonNull(
                        exception.getBindingResult().getFieldError()
                ).getDefaultMessage()
        );
    }
}
