package com.example.library.core.use_cases.books;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.use_cases.books.implementations.GetAllBooksUseCaseImplementation;
import com.example.library.infrastructure.gateways.BookRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class GetAllBooksUseCaseTest {

    @Autowired
    private BookRepository repository;

    @Mock
    private BookEntityMapper mapper;

    @Mock
    private BookRepositoryGateway gateway;

    @Mock
    private GetAllBooksUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
        gateway = new BookRepositoryGateway(repository, mapper);
        useCase = new GetAllBooksUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = repository.save(mapper.toEntity(TestBookConfiguration.book()));

        List<Book> books = useCase.execute();

        assert books.size() == 1;
        assert books.get(0).getTitle().equals(book.getTitle());
        assert books.get(0).getAuthor().equals(book.getAuthor());
        assert books.get(0).getIsbn().equals(book.getIsbn());
        assert books.get(0).getCategory().equals(book.getCategory());
        assert books.get(0).getPublishDate().equals(book.getPublishDate());
    }
}
