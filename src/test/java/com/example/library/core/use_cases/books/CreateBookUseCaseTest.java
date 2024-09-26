package com.example.library.core.use_cases.books;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.use_cases.books.implementations.CreateBookUseCaseImplementation;
import com.example.library.infrastructure.gateways.BookRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CreateBookUseCaseTest {

    @Autowired
    private BookRepository repository;

    @Mock
    private BookEntityMapper mapper;

    @Mock
    private BookRepositoryGateway gateway;

    @Mock
    private CreateBookUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
        gateway = new BookRepositoryGateway(repository, mapper);
        useCase = new CreateBookUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        Book book = TestBookConfiguration.book();

        Book newBook = useCase.execute(book);

        assert newBook != null;
        assert newBook.getTitle().equals(book.getTitle());
        assert newBook.getAuthor().equals(book.getAuthor());
        assert newBook.getIsbn().equals(book.getIsbn());
        assert newBook.getCategory().equals(book.getCategory());
        assert newBook.getPublishDate().equals(book.getPublishDate());
    }
}
