package com.example.library.core.use_cases.books;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.use_cases.books.implementations.GetBookUseCaseImplementation;
import com.example.library.infrastructure.gateways.BookRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GetBookUseCaseTest {

    @Autowired
    private BookRepository repository;

    @Mock
    private BookEntityMapper mapper;

    @Mock
    private BookRepositoryGateway gateway;

    @Mock
    private GetBookUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
        gateway = new BookRepositoryGateway(repository, mapper);
        useCase = new GetBookUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = repository.save(mapper.toEntity(TestBookConfiguration.book()));

        Book bookFound = useCase.execute(book.getId());

        assert bookFound.getId().equals(book.getId());
        assert bookFound.getTitle().equals(book.getTitle());
        assert bookFound.getAuthor().equals(book.getAuthor());
        assert bookFound.getIsbn().equals(book.getIsbn());
        assert bookFound.getCategory().equals(book.getCategory());
        assert bookFound.getPublishDate().equals(book.getPublishDate());
    }
}
