package com.example.library.infrastructure.gateways;

import com.example.library.core.entities.Book;
import com.example.library.core.gateways.BookGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookRepositoryGateway implements BookGateway {
    private final BookRepository repository;
    private final BookEntityMapper mapper;

    @Override
    public Book createBook(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity newBook = repository.save(entity);
        return mapper.toBook(newBook);
    }

    public Book saveBook(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity bookUpdated = repository.saveAndFlush(entity);
        return mapper.toBook(bookUpdated);
    }

    @Override
    public boolean deleteBook(Long id) {
        Optional<BookEntity> book = repository.findById(id);
        if (book.isPresent()) {
            repository.delete(book.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Book> getBook(Long id) {
        Optional<BookEntity> entity = repository.findBookById(id);
        return entity.map(mapper::toBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toBook)
                .collect(Collectors.toList());
    }
}
