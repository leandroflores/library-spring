package com.example.library.services;

import com.example.library.domain.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Transactional
    public Book createBook(Book book) {
        return repository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Optional<Book> updateBook(Long id, Book book) {
        Optional<Book> bookById = repository.findById(id);
        if (bookById.isPresent()) {
            Book bookFound = bookById.get();
            bookFound.setTitle(book.getTitle());
            bookFound.setAuthor(book.getAuthor());
            bookFound.setIsbn(book.getIsbn());
            bookFound.setCategory(book.getCategory());
            bookFound.setPublishDate(book.getPublishDate());
            return Optional.of(repository.save(bookFound));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteBook(Long id) {
        Optional<Book> bookById = repository.findById(id);
        if (bookById.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }
}
